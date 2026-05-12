package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import io.nicolaszurbuchen.turnstile.core.mvi.MviViewModel

class WelcomeViewModel : MviViewModel<WelcomeState, WelcomeTrigger, WelcomeIntent, WelcomeAction, WelcomeCommand, WelcomeEvent>(
    initialState = WelcomeState,
    reducer = WelcomeReducer,
) {
    override suspend fun executeCommand(command: WelcomeCommand) = Unit
}
