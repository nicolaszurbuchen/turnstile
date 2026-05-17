package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel,
    onSignedUp: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onSignedUpUpdated by rememberUpdatedState(onSignedUp)
    val onNavigateToSignInUpdated by rememberUpdatedState(onNavigateToSignIn)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignUpEvent.NavigateHome -> onSignedUpUpdated()
                SignUpEvent.NavigateToSignIn -> onNavigateToSignInUpdated()
            }
        }
    }

    SignUpScreen(
        state = state,
        onFullNameChange = { viewModel.sendIntent(SignUpIntent.FullNameChanged(it)) },
        onEmailChange = { viewModel.sendIntent(SignUpIntent.EmailChanged(it)) },
        onPasswordChange = { viewModel.sendIntent(SignUpIntent.PasswordChanged(it)) },
        onSubmit = { viewModel.sendIntent(SignUpIntent.Submit) },
        onSignInClick = { viewModel.sendIntent(SignUpIntent.SignInClicked) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
