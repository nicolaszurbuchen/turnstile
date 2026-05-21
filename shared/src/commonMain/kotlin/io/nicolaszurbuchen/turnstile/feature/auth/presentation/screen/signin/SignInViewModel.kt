package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.core.mvi.MviViewModel
import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignInViewModel(
    private val auth: UserIdentityRepository,
) : MviViewModel<SignInState, SignInTrigger, SignInIntent, SignInAction, SignInCommand, SignInEvent>(
        initialState = SignInState(),
        reducer = SignInReducer,
    ) {
    private var loginJob: Job? = null

    override suspend fun executeCommand(command: SignInCommand) {
        when (command) {
            is SignInCommand.CallLogin -> {
                // Cancel any in-flight login if the user submits again.
                loginJob?.cancel()
                loginJob =
                    viewModelScope.launch {
                        runCatching { auth.signInWithEmail(command.email, command.password) }
                            .onSuccess { user ->
                                dispatchAction(SignInAction.LoginSucceeded(user.id))
                            }.onFailure { e ->
                                dispatchAction(SignInAction.LoginFailedWith(e.message ?: "Unknown error"))
                            }
                    }
            }
        }
    }
}
