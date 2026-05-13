package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import io.nicolaszurbuchen.turnstile.core.mvi.Action
import io.nicolaszurbuchen.turnstile.core.mvi.Command
import io.nicolaszurbuchen.turnstile.core.mvi.Event
import io.nicolaszurbuchen.turnstile.core.mvi.Intent
import io.nicolaszurbuchen.turnstile.core.mvi.State
import org.jetbrains.compose.resources.StringResource

data class ForgotPasswordState(
    val email: String = "",
    val emailError: StringResource? = null,
    val loading: Boolean = false,
    val submitted: Boolean = false,
    val submitError: String? = null,
) : State {

    val canSubmit: Boolean
        get() = email.isNotBlank() &&
                emailError == null &&
                !loading &&
                !submitted
}

sealed interface ForgotPasswordTrigger

sealed interface ForgotPasswordIntent : ForgotPasswordTrigger, Intent {
    data class EmailChanged(val value: String) : ForgotPasswordIntent
    data object Submit : ForgotPasswordIntent
}

sealed interface ForgotPasswordAction : ForgotPasswordTrigger, Action {
    data object ResetEmailSent : ForgotPasswordAction
    data class ResetFailedWith(val message: String) : ForgotPasswordAction
}

sealed interface ForgotPasswordCommand : Command {
    data class CallRequestReset(val email: String) : ForgotPasswordCommand
}

sealed interface ForgotPasswordEvent : Event
