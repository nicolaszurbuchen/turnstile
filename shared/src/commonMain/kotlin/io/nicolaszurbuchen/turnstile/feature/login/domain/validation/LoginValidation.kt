package io.nicolaszurbuchen.turnstile.feature.login.domain.validation

sealed interface EmailValidationError {
    data object Required : EmailValidationError

    data object Invalid : EmailValidationError
}

sealed interface PasswordValidationError {
    data object Required : PasswordValidationError

    data object TooShort : PasswordValidationError
}

sealed interface UsernameValidationError {
    data object Required : UsernameValidationError

    data object TooShort : UsernameValidationError
}

object LoginValidation {
    fun validateEmail(email: String): EmailValidationError? =
        when {
            email.isBlank() -> EmailValidationError.Required
            !email.contains("@") -> EmailValidationError.Invalid
            else -> null
        }

    fun validatePassword(password: String): PasswordValidationError? =
        when {
            password.isBlank() -> PasswordValidationError.Required
            password.length < 8 -> PasswordValidationError.TooShort
            else -> null
        }

    fun validateUsername(username: String): UsernameValidationError? =
        when {
            username.isBlank() -> UsernameValidationError.Required
            username.length < 2 -> UsernameValidationError.TooShort
            else -> null
        }
}
