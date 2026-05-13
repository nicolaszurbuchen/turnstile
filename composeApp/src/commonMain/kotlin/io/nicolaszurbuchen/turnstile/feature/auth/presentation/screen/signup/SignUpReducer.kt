package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import io.nicolaszurbuchen.turnstile.core.mvi.Next
import io.nicolaszurbuchen.turnstile.core.mvi.Reducer
import org.jetbrains.compose.resources.StringResource
import turnstile.composeapp.generated.resources.Res
import turnstile.composeapp.generated.resources.auth_error_email_invalid
import turnstile.composeapp.generated.resources.auth_error_name_too_short
import turnstile.composeapp.generated.resources.auth_error_password_too_short

object SignUpReducer : Reducer<SignUpState, SignUpTrigger, SignUpCommand, SignUpEvent> {

    override fun reduce(
        state: SignUpState,
        trigger: SignUpTrigger,
    ): Next<SignUpState, SignUpCommand, SignUpEvent> = when (trigger) {
        is SignUpIntent -> reduceIntent(state, trigger)
        is SignUpAction -> reduceAction(state, trigger)
    }

    private fun reduceIntent(
        state: SignUpState,
        intent: SignUpIntent,
    ): Next<SignUpState, SignUpCommand, SignUpEvent> = when (intent) {

        is SignUpIntent.FullNameChanged -> Next(
            state = state.copy(
                fullName = intent.value,
                fullNameError = validateFullName(intent.value),
                submitError = null,
            )
        )

        is SignUpIntent.EmailChanged -> Next(
            state = state.copy(
                email = intent.value,
                emailError = validateEmail(intent.value),
                submitError = null,
            )
        )

        is SignUpIntent.PasswordChanged -> Next(
            state = state.copy(
                password = intent.value,
                passwordError = validatePassword(intent.value),
                submitError = null,
            )
        )

        SignUpIntent.Submit -> if (state.canSubmit) {
            Next(
                state = state.copy(loading = true, submitError = null),
                commands = listOf(
                    SignUpCommand.CallRegister(state.fullName, state.email, state.password)
                ),
            )
        } else {
            Next(state)
        }

        SignUpIntent.SignInClicked -> Next(
            state = state,
            events = listOf(SignUpEvent.NavigateToSignIn),
        )
    }

    private fun reduceAction(
        state: SignUpState,
        action: SignUpAction,
    ): Next<SignUpState, SignUpCommand, SignUpEvent> = when (action) {

        is SignUpAction.RegisterSucceeded -> Next(
            state = state.copy(loading = false),
            events = listOf(SignUpEvent.NavigateHome),
        )

        is SignUpAction.RegisterFailedWith -> Next(
            state = state.copy(loading = false, submitError = action.message),
        )
    }

    private fun validateFullName(name: String): StringResource? = when {
        name.isEmpty() -> null
        name.length < 2 -> Res.string.auth_error_name_too_short
        else -> null
    }

    private fun validateEmail(email: String): StringResource? = when {
        email.isEmpty() -> null
        !email.contains("@") -> Res.string.auth_error_email_invalid
        else -> null
    }

    private fun validatePassword(password: String): StringResource? = when {
        password.isEmpty() -> null
        password.length < 8 -> Res.string.auth_error_password_too_short
        else -> null
    }
}
