package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import io.nicolaszurbuchen.turnstile.core.mvi.Next
import io.nicolaszurbuchen.turnstile.core.mvi.Reducer

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

    private fun validateFullName(name: String): String? = when {
        name.isEmpty() -> null
        name.length < 2 -> "Name is too short"
        else -> null
    }

    private fun validateEmail(email: String): String? = when {
        email.isEmpty() -> null
        !email.contains("@") -> "Invalid email"
        else -> null
    }

    private fun validatePassword(password: String): String? = when {
        password.isEmpty() -> null
        password.length < 8 -> "Password must be at least 8 characters"
        else -> null
    }
}
