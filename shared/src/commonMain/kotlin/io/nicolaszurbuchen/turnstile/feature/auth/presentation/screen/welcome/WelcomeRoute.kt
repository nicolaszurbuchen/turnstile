package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WelcomeRoute(
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WelcomeScreen(
        onSignInClick = onNavigateToSignIn,
        onSignUpClick = onNavigateToSignUp,
        modifier = modifier,
    )
}
