package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository
import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val auth: UserIdentityRepository,
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
                            auth.signUpWithEmail(
                                username = command.username,
                                email = command.email,
                                password = command.password,
                            )
                        }.onSuccess { user ->
                            dispatchAction(SignUpAction.RegisterSucceeded(user.id))
                        }.onFailure { e ->
                            dispatchAction(SignUpAction.RegisterFailedWith(e.message ?: "Unknown error"))
                        }
                    }
            }
        }
    }
}
