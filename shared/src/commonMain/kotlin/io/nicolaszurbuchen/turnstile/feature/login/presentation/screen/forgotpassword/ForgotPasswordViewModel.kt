package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SendPasswordResetEmailUseCase
import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase,
) : MviViewModel<
        ForgotPasswordState,
        ForgotPasswordTrigger,
        ForgotPasswordIntent,
        ForgotPasswordAction,
        ForgotPasswordCommand,
        ForgotPasswordEvent,
    >(
        initialState = ForgotPasswordState(),
        reducer = ForgotPasswordReducer,
    ) {
    override suspend fun executeCommand(command: ForgotPasswordCommand) {
        when (command) {
            is ForgotPasswordCommand.CallRequestReset -> {
                viewModelScope.launch {
                    runCatching { sendPasswordResetEmailUseCase(command.email) }
                        .onSuccess {
                            dispatchAction(ForgotPasswordAction.ResetEmailSent)
                        }
                        .onFailure { e ->
                            dispatchAction(ForgotPasswordAction.ResetFailedWith(e.message ?: "Unknown error"))
                        }
                }
            }
        }
    }
}
