package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun WelcomeRoute(
    viewModel: WelcomeViewModel,
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                WelcomeEvent.NavigateToSignIn -> onNavigateToSignIn()
                WelcomeEvent.NavigateToSignUp -> onNavigateToSignUp()
            }
        }
    }

    WelcomeScreen(
        onSignInClicked = { viewModel.sendIntent(WelcomeIntent.SignInClicked) },
        onSignUpClicked = { viewModel.sendIntent(WelcomeIntent.SignUpClicked) },
        modifier = modifier,
    )
}
