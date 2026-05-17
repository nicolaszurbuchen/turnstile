package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import io.nicolaszurbuchen.turnstile.core.mvi.Next
import io.nicolaszurbuchen.turnstile.core.mvi.Reducer

object WelcomeReducer : Reducer<WelcomeState, WelcomeTrigger, WelcomeCommand, WelcomeEvent> {
    override fun reduce(
        state: WelcomeState,
        trigger: WelcomeTrigger,
    ): Next<WelcomeState, WelcomeCommand, WelcomeEvent> =
        when (trigger) {
            is WelcomeIntent -> {
                when (trigger) {
                    WelcomeIntent.SignInClicked -> Next(state, events = listOf(WelcomeEvent.NavigateToSignIn))
                    WelcomeIntent.SignUpClicked -> Next(state, events = listOf(WelcomeEvent.NavigateToSignUp))
                }
            }

            is WelcomeAction -> {
                Next(state)
            }
        }
}
