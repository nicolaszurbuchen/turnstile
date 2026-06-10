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
    onNavigateHome: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateHomeUpdated by rememberUpdatedState(onNavigateHome)
    val onNavigateToSignInUpdated by rememberUpdatedState(onNavigateToSignIn)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                SignUpLabel.NavigateHome -> onNavigateHomeUpdated()
                SignUpLabel.NavigateToSignIn -> onNavigateToSignInUpdated()
            }
        }
    }

    SignUpScreen(
        state = state,
        onUsernameChange = { viewModel.onIntent(SignUpIntent.UsernameChanged(it)) },
        onEmailChange = { viewModel.onIntent(SignUpIntent.EmailChanged(it)) },
        onPasswordChange = { viewModel.onIntent(SignUpIntent.PasswordChanged(it)) },
        onSubmit = { viewModel.onIntent(SignUpIntent.Submit) },
        onSignInClick = { viewModel.onIntent(SignUpIntent.SignInClicked) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
