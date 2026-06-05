package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import org.jetbrains.compose.resources.StringResource

data class SignInStateImpl(
    override val email: String = "",
    override val password: String = "",
    override val emailError: StringResource? = null,
    override val passwordError: StringResource? = null,
    override val rememberMe: Boolean = false,
    override val loading: Boolean = false,
    override val submitError: String? = null,
) : SignInState {
    override val canSubmit: Boolean
        get() =
            email.isNotBlank() &&
                password.isNotBlank() &&
                emailError == null &&
                passwordError == null &&
                !loading
}
