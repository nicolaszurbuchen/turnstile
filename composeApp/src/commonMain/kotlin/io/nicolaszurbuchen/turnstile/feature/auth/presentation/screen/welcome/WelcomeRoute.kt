package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier

@Composable
fun WelcomeRoute(
    viewModel: WelcomeViewModel,
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onNavigateToSignInUpdated by rememberUpdatedState(onNavigateToSignIn)
    val onNavigateToSignUpUpdated by rememberUpdatedState(onNavigateToSignUp)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                WelcomeEvent.NavigateToSignIn -> onNavigateToSignInUpdated
                WelcomeEvent.NavigateToSignUp -> onNavigateToSignUpUpdated
            }
        }
    }

    WelcomeScreen(
        onSignInClick = { viewModel.sendIntent(WelcomeIntent.SignInClicked) },
        onSignUpClick = { viewModel.sendIntent(WelcomeIntent.SignUpClicked) },
        modifier = modifier,
    )
}
