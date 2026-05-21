package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel

class ForgotPasswordViewModel :
    MviViewModel<
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
                // TODO: wire to AuthRepository
                dispatchAction(ForgotPasswordAction.ResetEmailSent)
            }
        }
    }
}
