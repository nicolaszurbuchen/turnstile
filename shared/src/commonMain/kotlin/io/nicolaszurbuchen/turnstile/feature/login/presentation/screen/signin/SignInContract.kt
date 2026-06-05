package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import io.nicolaszurbuchen.turnstile.infra.mvi.Action
import io.nicolaszurbuchen.turnstile.infra.mvi.Command
import io.nicolaszurbuchen.turnstile.infra.mvi.Event
import io.nicolaszurbuchen.turnstile.infra.mvi.Intent
import io.nicolaszurbuchen.turnstile.infra.mvi.State
import io.nicolaszurbuchen.turnstile.infra.mvi.Trigger
import org.jetbrains.compose.resources.StringResource

sealed interface SignInState : State {
    val email: String
    val password: String
    val emailError: StringResource?
    val passwordError: StringResource?
    val rememberMe: Boolean
    val loading: Boolean
    val submitError: String?
    val canSubmit: Boolean
}

sealed interface SignInTrigger : Trigger

sealed interface SignInIntent :
    SignInTrigger,
    Intent {
    data class EmailChanged(val value: String) : SignInIntent
    data class PasswordChanged(val value: String) : SignInIntent
    data object Submit : SignInIntent
    data object RememberMeToggled : SignInIntent
    data object SignUpClicked : SignInIntent
    data object ForgotPasswordClicked : SignInIntent
}

sealed interface SignInAction :
    SignInTrigger,
    Action {
    data object LoginSucceeded : SignInAction
    data class LoginFailedWith(val message: String) : SignInAction
}

sealed interface SignInCommand : Command {
    data class CallLogin(val email: String, val password: String) : SignInCommand
}

sealed interface SignInEvent : Event {
    data object NavigateHome : SignInEvent
    data object NavigateToSignUp : SignInEvent
    data object NavigateToForgotPassword : SignInEvent
}
