package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.core.mvi.MviViewModel
import io.nicolaszurbuchen.turnstile.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignInViewModel(
    private val auth: AuthRepository,
) : MviViewModel<SignInState, SignInTrigger, SignInIntent, SignInAction, SignInCommand, SignEvent>(
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
                        runCatching { auth.login(command.email, command.password) }
                            .onSuccess { token ->
                                dispatchAction(SignInAction.LoginSucceeded(token))
                            }.onFailure { e ->
                                dispatchAction(SignInAction.LoginFailedWith(e.message ?: "Unknown error"))
                            }
                    }
            }
        }
    }
}
