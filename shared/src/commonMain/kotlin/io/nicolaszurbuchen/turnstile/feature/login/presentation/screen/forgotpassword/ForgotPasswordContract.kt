package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_error_email_invalid
import turnstile.shared.generated.resources.auth_error_email_required

sealed interface ForgotPasswordIntent {
    data class EmailChanged(val value: String) : ForgotPasswordIntent
    data object Submit : ForgotPasswordIntent
    data object BackClicked : ForgotPasswordIntent
}

sealed interface ForgotPasswordLabel {
    data object NavigateBack : ForgotPasswordLabel
}

sealed interface ForgotPasswordAction

sealed interface ForgotPasswordMessage {
    data class EmailChanged(val value: String) : ForgotPasswordMessage
    data class SetError(val error: StringResource?) : ForgotPasswordMessage
    data object StartedLoading : ForgotPasswordMessage
    data object ResetEmailSent : ForgotPasswordMessage
    data class ResetFailed(val message: String) : ForgotPasswordMessage
}

data class ForgotPasswordState(
    val email: String = "",
    val emailError: StringResource? = null,
    val loading: Boolean = false,
    val submitted: Boolean = false,
    val submitError: String? = null,
) {
    val canSubmit: Boolean
        get() = !loading && !submitted
}

fun validateEmail(email: String): StringResource? =
    when {
        email.isBlank() -> Res.string.auth_error_email_required
        !email.contains("@") -> Res.string.auth_error_email_invalid
        else -> null
    }
