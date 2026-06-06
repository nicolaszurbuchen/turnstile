package io.nicolaszurbuchen.turnstile.feature.splash.domain.model

sealed class SessionStatus {
    data object Authenticated : SessionStatus()

    data object Unauthenticated : SessionStatus()
}
