package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import io.nicolaszurbuchen.turnstile.infra.mvi.Next
import io.nicolaszurbuchen.turnstile.infra.mvi.Reducer
import org.jetbrains.compose.resources.StringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_error_email_invalid
import turnstile.shared.generated.resources.auth_error_password_too_short

object SignInReducer : Reducer<SignInState, SignInTrigger, SignInCommand, SignInEvent> {
    override fun reduce(
        state: SignInState,
        trigger: SignInTrigger,
    ): Next<SignInState, SignInCommand, SignInEvent> =
        when (trigger) {
            is SignInIntent -> reduceIntent(state, trigger)
            is SignInAction -> reduceAction(state, trigger)
        }

    private fun reduceIntent(
        state: SignInState,
        intent: SignInIntent,
    ): Next<SignInState, SignInCommand, SignInEvent> =
        when (intent) {
            is SignInIntent.EmailChanged -> {
                Next(
                    state =
                        state.copy(
                            email = intent.value,
                            emailError = validateEmail(intent.value),
                            submitError = null,
                        ),
                )
            }

            is SignInIntent.PasswordChanged -> {
                Next(
                    state =
                        state.copy(
                            password = intent.value,
                            passwordError = validatePassword(intent.value),
                            submitError = null,
                        ),
                )
            }

            SignInIntent.RememberMeToggled -> {
                Next(
                    state = state.copy(rememberMe = !state.rememberMe),
                )
            }

            SignInIntent.Submit -> {
                if (state.canSubmit) {
                    Next(
                        state = state.copy(loading = true, submitError = null),
                        commands = listOf(SignInCommand.CallLogin(state.email, state.password)),
                    )
                } else {
                    Next(state)
                }
            }

            SignInIntent.SignUpClicked -> {
                Next(
                    state = state,
                    events = listOf(SignInEvent.NavigateToSignUp),
                )
            }

            SignInIntent.ForgotPasswordClicked -> {
                Next(
                    state = state,
                    events = listOf(SignInEvent.NavigateToForgotPassword),
                )
            }
        }

    private fun reduceAction(
        state: SignInState,
        action: SignInAction,
    ): Next<SignInState, SignInCommand, SignInEvent> =
        when (action) {
            is SignInAction.LoginSucceeded -> {
                Next(
                    state = state.copy(loading = false),
                    events = listOf(SignInEvent.NavigateHome),
                )
            }

            is SignInAction.LoginFailedWith -> {
                Next(
                    state = state.copy(loading = false, submitError = action.message),
                )
            }
        }

    private fun validateEmail(email: String): StringResource? =
        when {
            email.isEmpty() -> null
            !email.contains("@") -> Res.string.auth_error_email_invalid
            else -> null
        }

    private fun validatePassword(password: String): StringResource? =
        when {
            password.isEmpty() -> null
            password.length < 8 -> Res.string.auth_error_password_too_short
            else -> null
        }
}
