package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_error_email_invalid
import turnstile.shared.generated.resources.auth_error_password_too_short

sealed interface SignInIntent {
    data class EmailChanged(val value: String) : SignInIntent
    data class PasswordChanged(val value: String) : SignInIntent
    data object Submit : SignInIntent
    data object RememberMeToggled : SignInIntent
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
    data class EmailChanged(val value: String, val error: StringResource?) : SignInMessage
    data class PasswordChanged(val value: String, val error: StringResource?) : SignInMessage
    data object RememberMeToggled : SignInMessage
    data object StartedLoading : SignInMessage
    data object LoginSucceeded : SignInMessage
    data class LoginFailed(val message: String) : SignInMessage
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
        get() =
            email.isNotBlank() &&
                password.isNotBlank() &&
                emailError == null &&
                passwordError == null &&
                !loading
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
