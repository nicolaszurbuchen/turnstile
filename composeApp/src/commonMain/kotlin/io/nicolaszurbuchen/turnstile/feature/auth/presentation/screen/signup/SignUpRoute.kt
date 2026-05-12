package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel,
    onRegistered: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignUpEvent.NavigateHome -> onRegistered()
                SignUpEvent.NavigateToSignIn -> onNavigateToSignIn()
            }
        }
    }

    SignUpScreen(
        state = state,
        onFullNameChanged = { viewModel.sendIntent(SignUpIntent.FullNameChanged(it)) },
        onEmailChanged = { viewModel.sendIntent(SignUpIntent.EmailChanged(it)) },
        onPasswordChanged = { viewModel.sendIntent(SignUpIntent.PasswordChanged(it)) },
        onSubmitted = { viewModel.sendIntent(SignUpIntent.Submit) },
        onSignInClicked = { viewModel.sendIntent(SignUpIntent.SignInClicked) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
