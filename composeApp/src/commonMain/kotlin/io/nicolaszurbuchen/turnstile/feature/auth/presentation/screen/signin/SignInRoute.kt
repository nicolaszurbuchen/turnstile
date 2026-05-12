package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInRoute(
    viewModel: SignInViewModel,
    onLoggedIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignEvent.NavigateHome -> onLoggedIn()
                SignEvent.NavigateToSignUp -> onNavigateToSignUp()
                SignEvent.NavigateToForgotPassword -> onNavigateToForgotPassword()
            }
        }
    }

    SignInScreen(
        state = state,
        onEmailChanged = { viewModel.sendIntent(SignInIntent.EmailChanged(it)) },
        onPasswordChanged = { viewModel.sendIntent(SignInIntent.PasswordChanged(it)) },
        onRememberMeToggled = { viewModel.sendIntent(SignInIntent.RememberMeToggled) },
        onSubmitted = { viewModel.sendIntent(SignInIntent.Submit) },
        onForgotPasswordClicked = { viewModel.sendIntent(SignInIntent.ForgotPasswordClicked) },
        onSignUpClicked = { viewModel.sendIntent(SignInIntent.SignUpClicked) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
