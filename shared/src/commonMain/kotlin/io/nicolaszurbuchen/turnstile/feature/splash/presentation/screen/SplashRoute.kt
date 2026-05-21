package io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

@Composable
fun SplashRoute(
    viewModel: SplashViewModel,
    onNavigateToAuth: () -> Unit,
    onNavigateToVault: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.status) {
        when (state.status) {
            SessionStatus.Authenticated -> onNavigateToVault()
            SessionStatus.Unauthenticated -> onNavigateToAuth()
            null -> Unit
        }
    }
}