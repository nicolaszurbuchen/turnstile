package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import io.nicolaszurbuchen.turnstile.core.mvi.Action
import io.nicolaszurbuchen.turnstile.core.mvi.Command
import io.nicolaszurbuchen.turnstile.core.mvi.Event
import io.nicolaszurbuchen.turnstile.core.mvi.Intent
import io.nicolaszurbuchen.turnstile.core.mvi.State

object WelcomeState : State

sealed interface WelcomeTrigger

sealed interface WelcomeIntent : WelcomeTrigger, Intent {
    data object SignInClicked : WelcomeIntent
    data object SignUpClicked : WelcomeIntent
}

sealed interface WelcomeAction : WelcomeTrigger, Action

sealed interface WelcomeCommand : Command

sealed interface WelcomeEvent : Event {
    data object NavigateToSignIn : WelcomeEvent
    data object NavigateToSignUp : WelcomeEvent
}
