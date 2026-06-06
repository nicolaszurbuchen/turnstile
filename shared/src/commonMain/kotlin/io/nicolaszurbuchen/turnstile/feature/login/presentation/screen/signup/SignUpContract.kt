package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import io.nicolaszurbuchen.turnstile.infra.mvi.Action
import io.nicolaszurbuchen.turnstile.infra.mvi.Command
import io.nicolaszurbuchen.turnstile.infra.mvi.Event
import io.nicolaszurbuchen.turnstile.infra.mvi.Intent
import io.nicolaszurbuchen.turnstile.infra.mvi.State
import io.nicolaszurbuchen.turnstile.infra.mvi.Trigger
import org.jetbrains.compose.resources.StringResource

sealed interface SignUpState : State {
    val username: String
    val email: String
    val password: String
    val usernameError: StringResource?
    val emailError: StringResource?
    val passwordError: StringResource?
    val loading: Boolean
    val submitError: String?
    val canSubmit: Boolean
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
    data object RegisterSucceeded : SignUpAction

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
