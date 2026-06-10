package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import io.nicolaszurbuchen.turnstile.feature.login.domain.validation.EmailValidationError
import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.login_error_email_invalid
import turnstile.shared.generated.resources.login_error_email_required

sealed interface ForgotPasswordIntent {
    data class EmailChanged(
        val value: String,
    ) : ForgotPasswordIntent

    data object Submit : ForgotPasswordIntent

    data object BackClicked : ForgotPasswordIntent
}

sealed interface ForgotPasswordLabel {
    data object NavigateBack : ForgotPasswordLabel
}

sealed interface ForgotPasswordAction

sealed interface ForgotPasswordMessage {
    data class EmailChanged(
        val value: String,
    ) : ForgotPasswordMessage

    data class SetError(
        val error: StringResource?,
    ) : ForgotPasswordMessage

    data object StartedLoading : ForgotPasswordMessage

    data object ResetEmailSent : ForgotPasswordMessage

    data class ResetFailed(
        val message: String,
    ) : ForgotPasswordMessage
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

fun EmailValidationError.asStringResource(): StringResource =
    when (this) {
        EmailValidationError.Required -> Res.string.login_error_email_required
        EmailValidationError.Invalid -> Res.string.login_error_email_invalid
    }
