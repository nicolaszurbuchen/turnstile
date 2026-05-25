package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import org.jetbrains.compose.resources.StringResource

data class ForgotPasswordStateImpl(
    override val email: String = "",
    override val emailError: StringResource? = null,
    override val loading: Boolean = false,
    override val submitted: Boolean = false,
    override val submitError: String? = null,
) : ForgotPasswordState {
    override val canSubmit: Boolean
        get() =
            email.isNotBlank() &&
                emailError == null &&
                !loading &&
                !submitted
}
