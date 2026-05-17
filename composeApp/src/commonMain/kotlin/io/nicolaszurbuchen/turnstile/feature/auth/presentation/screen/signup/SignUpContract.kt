package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import io.nicolaszurbuchen.turnstile.core.mvi.Action
import io.nicolaszurbuchen.turnstile.core.mvi.Command
import io.nicolaszurbuchen.turnstile.core.mvi.Event
import io.nicolaszurbuchen.turnstile.core.mvi.Intent
import io.nicolaszurbuchen.turnstile.core.mvi.State
import org.jetbrains.compose.resources.StringResource

data class SignUpState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val fullNameError: StringResource? = null,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val loading: Boolean = false,
    val submitError: String? = null,
) : State {
    val canSubmit: Boolean
        get() =
            fullName.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                fullNameError == null &&
                emailError == null &&
                passwordError == null &&
                !loading
}

sealed interface SignUpTrigger

sealed interface SignUpIntent :
    SignUpTrigger,
    Intent {
    data class FullNameChanged(
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
        val fullName: String,
        val email: String,
        val password: String,
    ) : SignUpCommand
}

sealed interface SignUpEvent : Event {
    data object NavigateHome : SignUpEvent

    data object NavigateToSignIn : SignUpEvent
}
