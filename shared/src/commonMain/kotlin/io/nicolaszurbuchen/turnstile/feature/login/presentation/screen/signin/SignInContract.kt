package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import io.nicolaszurbuchen.turnstile.feature.login.domain.validation.EmailValidationError
import io.nicolaszurbuchen.turnstile.feature.login.domain.validation.PasswordValidationError
import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.login_error_email_invalid
import turnstile.shared.generated.resources.login_error_email_required
import turnstile.shared.generated.resources.login_error_password_required
import turnstile.shared.generated.resources.login_error_password_too_short

sealed interface SignInIntent {
    data class EmailChanged(
        val value: String,
    ) : SignInIntent

    data class PasswordChanged(
        val value: String,
    ) : SignInIntent

    data object Submit : SignInIntent

    data object SignUpClicked : SignInIntent

    data object ForgotPasswordClicked : SignInIntent
}

sealed interface SignInLabel {
    data object NavigateHome : SignInLabel

    data object NavigateToSignUp : SignInLabel

    data object NavigateToForgotPassword : SignInLabel
}

sealed interface SignInAction

sealed interface SignInMessage {
    data class EmailChanged(
        val value: String,
    ) : SignInMessage

    data class PasswordChanged(
        val value: String,
    ) : SignInMessage

    data class SetErrors(
        val emailError: StringResource?,
        val passwordError: StringResource?,
    ) : SignInMessage

    data object StartedLoading : SignInMessage

    data object LoginSucceeded : SignInMessage

    data class LoginFailed(
        val message: String,
    ) : SignInMessage
}

data class SignInState(
    val email: String = "",
    val password: String = "",
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val rememberMe: Boolean = false,
    val loading: Boolean = false,
    val submitError: String? = null,
) {
    val canSubmit: Boolean
        get() = !loading
}

fun EmailValidationError.asStringResource(): StringResource =
    when (this) {
        EmailValidationError.Required -> Res.string.login_error_email_required
        EmailValidationError.Invalid -> Res.string.login_error_email_invalid
    }

fun PasswordValidationError.asStringResource(): StringResource =
    when (this) {
        PasswordValidationError.Required -> Res.string.login_error_password_required
        PasswordValidationError.TooShort -> Res.string.login_error_password_too_short
    }
