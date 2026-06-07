package io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashRoute(
    onNavigateToAuth: () -> Unit,
    onNavigateToVault: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val onNavigateToAuthUpdated by rememberUpdatedState(onNavigateToAuth)
    val onNavigateToVaultUpdated by rememberUpdatedState(onNavigateToVault)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                SplashLabel.NavigateToAuth -> onNavigateToAuthUpdated()
                SplashLabel.NavigateToVault -> onNavigateToVaultUpdated()
            }
        }
    }
}
