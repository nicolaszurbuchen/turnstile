package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import io.nicolaszurbuchen.turnstile.feature.login.domain.validation.EmailValidationError
import io.nicolaszurbuchen.turnstile.feature.login.domain.validation.PasswordValidationError
import io.nicolaszurbuchen.turnstile.feature.login.domain.validation.UsernameValidationError
import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_error_email_invalid
import turnstile.shared.generated.resources.auth_error_email_required
import turnstile.shared.generated.resources.auth_error_password_required
import turnstile.shared.generated.resources.auth_error_password_too_short
import turnstile.shared.generated.resources.auth_error_username_required
import turnstile.shared.generated.resources.auth_error_username_too_short

sealed interface SignUpIntent {
    data class UsernameChanged(val value: String) : SignUpIntent
    data class EmailChanged(val value: String) : SignUpIntent
    data class PasswordChanged(val value: String) : SignUpIntent
    data object Submit : SignUpIntent
    data object SignInClicked : SignUpIntent
}

sealed interface SignUpLabel {
    data object NavigateHome : SignUpLabel
    data object NavigateToSignIn : SignUpLabel
}

sealed interface SignUpAction

sealed interface SignUpMessage {
    data class UsernameChanged(val value: String) : SignUpMessage
    data class EmailChanged(val value: String) : SignUpMessage
    data class PasswordChanged(val value: String) : SignUpMessage
    data class SetErrors(
        val usernameError: StringResource?,
        val emailError: StringResource?,
        val passwordError: StringResource?,
    ) : SignUpMessage
    data object StartedLoading : SignUpMessage
    data object RegisterSucceeded : SignUpMessage
    data class RegisterFailed(val message: String) : SignUpMessage
}

data class SignUpState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val usernameError: StringResource? = null,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val loading: Boolean = false,
    val submitError: String? = null,
) {
    val canSubmit: Boolean
        get() = !loading
}

fun UsernameValidationError.asStringResource(): StringResource =
    when (this) {
        UsernameValidationError.Required -> Res.string.auth_error_username_required
        UsernameValidationError.TooShort -> Res.string.auth_error_username_too_short
    }

fun EmailValidationError.asStringResource(): StringResource =
    when (this) {
        EmailValidationError.Required -> Res.string.auth_error_email_required
        EmailValidationError.Invalid -> Res.string.auth_error_email_invalid
    }

fun PasswordValidationError.asStringResource(): StringResource =
    when (this) {
        PasswordValidationError.Required -> Res.string.auth_error_password_required
        PasswordValidationError.TooShort -> Res.string.auth_error_password_too_short
    }
