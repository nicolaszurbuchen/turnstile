package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpRoute(
    onSignedUp: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
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
        onUsernameChange = { viewModel.sendIntent(SignUpIntent.UsernameChanged(it)) },
        onEmailChange = { viewModel.sendIntent(SignUpIntent.EmailChanged(it)) },
        onPasswordChange = { viewModel.sendIntent(SignUpIntent.PasswordChanged(it)) },
        onSubmit = { viewModel.sendIntent(SignUpIntent.Submit) },
        onSignInClick = { viewModel.sendIntent(SignUpIntent.SignInClicked) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
