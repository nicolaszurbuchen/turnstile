package io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

@Composable
fun SplashRoute(
    viewModel: SplashViewModel,
    onNavigateToAuth: () -> Unit,
    onNavigateToVault: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateToAuthUpdated by rememberUpdatedState(onNavigateToAuth)
    val onNavigateToVaultUpdated by rememberUpdatedState(onNavigateToVault)

    LaunchedEffect(state.status) {
        when (state.status) {
            SessionStatus.Authenticated -> onNavigateToAuthUpdated()
            SessionStatus.Unauthenticated -> onNavigateToVaultUpdated()
            null -> Unit
        }
    }
}
