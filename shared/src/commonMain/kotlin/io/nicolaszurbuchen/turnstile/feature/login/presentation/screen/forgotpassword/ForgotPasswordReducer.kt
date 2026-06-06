package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import io.nicolaszurbuchen.turnstile.infra.mvi.Next
import io.nicolaszurbuchen.turnstile.infra.mvi.Reducer
import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_error_email_invalid

object ForgotPasswordReducer :
    Reducer<ForgotPasswordState, ForgotPasswordTrigger, ForgotPasswordCommand, ForgotPasswordEvent> {
    override fun reduce(
        state: ForgotPasswordState,
        trigger: ForgotPasswordTrigger,
    ): Next<ForgotPasswordState, ForgotPasswordCommand, ForgotPasswordEvent> =
        when (trigger) {
            is ForgotPasswordIntent -> reduceIntent(state, trigger)
            is ForgotPasswordAction -> reduceAction(state, trigger)
        }

    private fun reduceIntent(
        state: ForgotPasswordState,
        intent: ForgotPasswordIntent,
    ): Next<ForgotPasswordState, ForgotPasswordCommand, ForgotPasswordEvent> =
        when (intent) {
            is ForgotPasswordIntent.EmailChanged -> {
                Next(
                    state =
                        state.copy(
                            email = intent.value,
                            emailError = validateEmail(intent.value),
                            submitError = null,
                        ),
                )
            }

            ForgotPasswordIntent.Submit -> {
                if (state.canSubmit) {
                    Next(
                        state = state.copy(loading = true, submitError = null),
                        commands = listOf(ForgotPasswordCommand.CallRequestReset(state.email)),
                    )
                } else {
                    Next(state)
                }
            }
        }

    private fun reduceAction(
        state: ForgotPasswordState,
        action: ForgotPasswordAction,
    ): Next<ForgotPasswordState, ForgotPasswordCommand, ForgotPasswordEvent> =
        when (action) {
            ForgotPasswordAction.ResetEmailSent -> {
                Next(
                    state = state.copy(loading = false, submitted = true),
                )
            }

            is ForgotPasswordAction.ResetFailedWith -> {
                Next(
                    state = state.copy(loading = false, submitError = action.message),
                )
            }
        }

    private fun validateEmail(email: String): StringResource? =
        when {
            email.isEmpty() -> null
            !email.contains("@") -> Res.string.auth_error_email_invalid
            else -> null
        }
}
