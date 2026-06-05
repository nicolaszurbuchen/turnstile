package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import org.jetbrains.compose.resources.StringResource

data class SignUpStateImpl(
    override val username: String = "",
    override val email: String = "",
    override val password: String = "",
    override val usernameError: StringResource? = null,
    override val emailError: StringResource? = null,
    override val passwordError: StringResource? = null,
    override val loading: Boolean = false,
    override val submitError: String? = null,
) : SignUpState {
    override val canSubmit: Boolean
        get() =
            username.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                usernameError == null &&
                emailError == null &&
                passwordError == null &&
                !loading
}
