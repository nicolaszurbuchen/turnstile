package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_error_email_invalid
import turnstile.shared.generated.resources.auth_error_password_too_short
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
    data class UsernameChanged(val value: String, val error: StringResource?) : SignUpMessage
    data class EmailChanged(val value: String, val error: StringResource?) : SignUpMessage
    data class PasswordChanged(val value: String, val error: StringResource?) : SignUpMessage
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
        get() =
            username.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                usernameError == null &&
                emailError == null &&
                passwordError == null &&
                !loading
}

fun validateUsername(name: String): StringResource? =
    when {
        name.isEmpty() -> null
        name.length < 2 -> Res.string.auth_error_username_too_short
        else -> null
    }

fun validateEmail(email: String): StringResource? =
    when {
        email.isEmpty() -> null
        !email.contains("@") -> Res.string.auth_error_email_invalid
        else -> null
    }

fun validatePassword(password: String): StringResource? =
    when {
        password.isEmpty() -> null
        password.length < 8 -> Res.string.auth_error_password_too_short
        else -> null
    }
