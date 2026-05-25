package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel

class ForgotPasswordViewModel :
    MviViewModel<
        ForgotPasswordStateImpl,
        ForgotPasswordTrigger,
        ForgotPasswordIntent,
        ForgotPasswordAction,
        ForgotPasswordCommand,
        ForgotPasswordEvent,
    >(
        initialState = ForgotPasswordStateImpl(),
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
