package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInRoute(
    viewModel: SignInViewModel,
    onSignedIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onSignedInUpdated by rememberUpdatedState(onSignedIn)
    val onNavigateToSignUpUpdated by rememberUpdatedState(onNavigateToSignUp)
    val onNavigateToForgotPasswordUpdated by rememberUpdatedState(onNavigateToForgotPassword)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignEvent.NavigateHome -> onSignedInUpdated
                SignEvent.NavigateToSignUp -> onNavigateToSignUpUpdated
                SignEvent.NavigateToForgotPassword -> onNavigateToForgotPasswordUpdated
            }
        }
    }

    SignInScreen(
        state = state,
        onEmailChang = { viewModel.sendIntent(SignInIntent.EmailChanged(it)) },
        onPasswordChange = { viewModel.sendIntent(SignInIntent.PasswordChanged(it)) },
        onRememberMeToggle = { viewModel.sendIntent(SignInIntent.RememberMeToggled) },
        onSubmit = { viewModel.sendIntent(SignInIntent.Submit) },
        onForgotPasswordClick = { viewModel.sendIntent(SignInIntent.ForgotPasswordClicked) },
        onSignUpClick = { viewModel.sendIntent(SignInIntent.SignUpClicked) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
