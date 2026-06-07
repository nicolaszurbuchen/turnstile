package io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen

import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

sealed interface SplashIntent

sealed interface SplashLabel {
    data object NavigateToAuth : SplashLabel
    data object NavigateToVault : SplashLabel
}

sealed interface SplashAction {
    data object CheckSession : SplashAction
}

sealed interface SplashMessage {
    data class SessionResolved(val status: SessionStatus) : SplashMessage
}

data class SplashState(
    val status: SessionStatus? = null,
)
