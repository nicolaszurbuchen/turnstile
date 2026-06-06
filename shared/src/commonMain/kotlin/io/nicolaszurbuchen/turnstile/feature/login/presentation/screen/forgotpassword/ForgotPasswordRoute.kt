package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ForgotPasswordRoute(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateBackUpdated by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                ForgotPasswordLabel.NavigateBack -> onNavigateBackUpdated()
            }
        }
    }

    ForgotPasswordScreen(
        state = state,
        onEmailChanged = { viewModel.onIntent(ForgotPasswordIntent.EmailChanged(it)) },
        onSubmit = { viewModel.onIntent(ForgotPasswordIntent.Submit) },
        onNavigateBack = { viewModel.onIntent(ForgotPasswordIntent.BackClicked) },
        modifier = modifier,
    )
}
