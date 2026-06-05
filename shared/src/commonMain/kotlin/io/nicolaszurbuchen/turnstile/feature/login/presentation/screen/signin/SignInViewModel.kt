package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignInWithEmailUseCase
import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
) : MviViewModel<SignInStateImpl, SignInTrigger, SignInIntent, SignInAction, SignInCommand, SignInEvent>(
        initialState = SignInStateImpl(),
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
                        runCatching { signInWithEmailUseCase(command.email, command.password) }
                            .onSuccess { user ->
                                dispatchAction(SignInAction.LoginSucceeded)
                            }.onFailure { e ->
                                dispatchAction(SignInAction.LoginFailedWith(e.message ?: "Unknown error"))
                            }
                    }
            }
        }
    }
}
