package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import io.nicolaszurbuchen.turnstile.infra.mvi.Action
import io.nicolaszurbuchen.turnstile.infra.mvi.Command
import io.nicolaszurbuchen.turnstile.infra.mvi.Event
import io.nicolaszurbuchen.turnstile.infra.mvi.Intent
import io.nicolaszurbuchen.turnstile.infra.mvi.State
import io.nicolaszurbuchen.turnstile.infra.mvi.Trigger
import org.jetbrains.compose.resources.StringResource

data class SignUpState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val usernameError: StringResource? = null,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val loading: Boolean = false,
    val submitError: String? = null,
) : State {
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

sealed interface SignUpTrigger : Trigger

sealed interface SignUpIntent :
    SignUpTrigger,
    Intent {
    data class UsernameChanged(
        val value: String,
    ) : SignUpIntent

    data class EmailChanged(
        val value: String,
    ) : SignUpIntent

    data class PasswordChanged(
        val value: String,
    ) : SignUpIntent

    data object Submit : SignUpIntent

    data object SignInClicked : SignUpIntent
}

sealed interface SignUpAction :
    SignUpTrigger,
    Action {
    data class RegisterSucceeded(
        val token: String,
    ) : SignUpAction

    data class RegisterFailedWith(
        val message: String,
    ) : SignUpAction
}

sealed interface SignUpCommand : Command {
    data class CallRegister(
        val username: String,
        val email: String,
        val password: String,
    ) : SignUpCommand
}

sealed interface SignUpEvent : Event {
    data object NavigateHome : SignUpEvent

    data object NavigateToSignIn : SignUpEvent
}
