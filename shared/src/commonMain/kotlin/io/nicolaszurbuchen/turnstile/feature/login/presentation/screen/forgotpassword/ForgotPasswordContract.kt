package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import io.nicolaszurbuchen.turnstile.infra.mvi.Action
import io.nicolaszurbuchen.turnstile.infra.mvi.Command
import io.nicolaszurbuchen.turnstile.infra.mvi.Event
import io.nicolaszurbuchen.turnstile.infra.mvi.Intent
import io.nicolaszurbuchen.turnstile.infra.mvi.State
import io.nicolaszurbuchen.turnstile.infra.mvi.Trigger
import org.jetbrains.compose.resources.StringResource

data class ForgotPasswordState(
    val email: String = "",
    val emailError: StringResource? = null,
    val loading: Boolean = false,
    val submitted: Boolean = false,
    val submitError: String? = null,
) : State {
    val canSubmit: Boolean
        get() =
            email.isNotBlank() &&
                emailError == null &&
                !loading &&
                !submitted
}

sealed interface ForgotPasswordTrigger : Trigger

sealed interface ForgotPasswordIntent :
    ForgotPasswordTrigger,
    Intent {
    data class EmailChanged(
        val value: String,
    ) : ForgotPasswordIntent

    data object Submit : ForgotPasswordIntent
}

sealed interface ForgotPasswordAction :
    ForgotPasswordTrigger,
    Action {
    data object ResetEmailSent : ForgotPasswordAction

    data class ResetFailedWith(
        val message: String,
    ) : ForgotPasswordAction
}

sealed interface ForgotPasswordCommand : Command {
    data class CallRequestReset(
        val email: String,
    ) : ForgotPasswordCommand
}

sealed interface ForgotPasswordEvent : Event
