package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import org.jetbrains.compose.resources.StringResource

data class ForgotPasswordState(
    val email: String = "",
    val emailError: StringResource? = null,
    val loading: Boolean = false,
    val submitted: Boolean = false,
    val submitError: String? = null,
) : io.nicolaszurbuchen.turnstile.infra.mvi.State {
    val canSubmit: Boolean
        get() =
            email.isNotBlank() &&
                emailError == null &&
                !loading &&
                !submitted
}

sealed interface ForgotPasswordTrigger : io.nicolaszurbuchen.turnstile.infra.mvi.Trigger

sealed interface ForgotPasswordIntent :
    ForgotPasswordTrigger,
    io.nicolaszurbuchen.turnstile.infra.mvi.Intent {
    data class EmailChanged(
        val value: String,
    ) : ForgotPasswordIntent

    data object Submit : ForgotPasswordIntent
}

sealed interface ForgotPasswordAction :
    ForgotPasswordTrigger,
    io.nicolaszurbuchen.turnstile.infra.mvi.Action {
    data object ResetEmailSent : ForgotPasswordAction

    data class ResetFailedWith(
        val message: String,
    ) : ForgotPasswordAction
}

sealed interface ForgotPasswordCommand : io.nicolaszurbuchen.turnstile.infra.mvi.Command {
    data class CallRequestReset(
        val email: String,
    ) : ForgotPasswordCommand
}

sealed interface ForgotPasswordEvent : io.nicolaszurbuchen.turnstile.infra.mvi.Event
