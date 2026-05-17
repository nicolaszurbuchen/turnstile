package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin

import io.nicolaszurbuchen.turnstile.core.mvi.Action
import io.nicolaszurbuchen.turnstile.core.mvi.Command
import io.nicolaszurbuchen.turnstile.core.mvi.Event
import io.nicolaszurbuchen.turnstile.core.mvi.Intent
import io.nicolaszurbuchen.turnstile.core.mvi.State
import org.jetbrains.compose.resources.StringResource

data class SignInState(
    val email: String = "",
    val password: String = "",
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val rememberMe: Boolean = false,
    val loading: Boolean = false,
    val submitError: String? = null,
) : State {
    val canSubmit: Boolean
        get() =
            email.isNotBlank() &&
                password.isNotBlank() &&
                emailError == null &&
                passwordError == null &&
                !loading
}

sealed interface SignInTrigger

sealed interface SignInIntent :
    SignInTrigger,
    Intent {
    data class EmailChanged(
        val value: String,
    ) : SignInIntent

    data class PasswordChanged(
        val value: String,
    ) : SignInIntent

    data object Submit : SignInIntent

    data object RememberMeToggled : SignInIntent

    data object SignUpClicked : SignInIntent

    data object ForgotPasswordClicked : SignInIntent
}

sealed interface SignInAction :
    SignInTrigger,
    Action {
    data class LoginSucceeded(
        val token: String,
    ) : SignInAction

    data class LoginFailedWith(
        val message: String,
    ) : SignInAction
}

sealed interface SignInCommand : Command {
    data class CallLogin(
        val email: String,
        val password: String,
    ) : SignInCommand
}

sealed interface SignEvent : Event {
    data object NavigateHome : SignEvent

    data object NavigateToSignUp : SignEvent

    data object NavigateToForgotPassword : SignEvent
}
