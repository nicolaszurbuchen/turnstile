package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInRoute(
    onNavigateHome: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateHomeUpdated by rememberUpdatedState(onNavigateHome)
    val onNavigateToSignUpUpdated by rememberUpdatedState(onNavigateToSignUp)
    val onNavigateToForgotPasswordUpdated by rememberUpdatedState(onNavigateToForgotPassword)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                SignInLabel.NavigateHome -> onNavigateHomeUpdated()
                SignInLabel.NavigateToSignUp -> onNavigateToSignUpUpdated()
                SignInLabel.NavigateToForgotPassword -> onNavigateToForgotPasswordUpdated()
            }
        }
    }

    SignInScreen(
        state = state,
        onEmailChanged = { viewModel.onIntent(SignInIntent.EmailChanged(it)) },
        onPasswordChanged = { viewModel.onIntent(SignInIntent.PasswordChanged(it)) },
        onSubmit = { viewModel.onIntent(SignInIntent.Submit) },
        onSignUpClicked = { viewModel.onIntent(SignInIntent.SignUpClicked) },
        onForgotPasswordClicked = { viewModel.onIntent(SignInIntent.ForgotPasswordClicked) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
