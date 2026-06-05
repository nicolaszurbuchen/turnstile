package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignUpWithEmailUseCase
import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
) : MviViewModel<SignUpStateImpl, SignUpTrigger, SignUpIntent, SignUpAction, SignUpCommand, SignUpEvent>(
        initialState = SignUpStateImpl(),
        reducer = SignUpReducer,
    ) {
    private var registerJob: Job? = null

    override suspend fun executeCommand(command: SignUpCommand) {
        when (command) {
            is SignUpCommand.CallRegister -> {
                registerJob?.cancel()
                registerJob =
                    viewModelScope.launch {
                        runCatching {
                            signUpWithEmailUseCase(
                                username = command.username,
                                email = command.email,
                                password = command.password,
                            )
                        }.onSuccess { user ->
                            dispatchAction(SignUpAction.RegisterSucceeded)
                        }.onFailure { e ->
                            dispatchAction(SignUpAction.RegisterFailedWith(e.message ?: "Unknown error"))
                        }
                    }
            }
        }
    }
}
