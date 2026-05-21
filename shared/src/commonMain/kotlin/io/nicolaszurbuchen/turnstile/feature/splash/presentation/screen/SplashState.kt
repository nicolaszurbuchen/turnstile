package io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen

import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

data class SplashState(
    val status: SessionStatus? = null,
)