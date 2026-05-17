package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ForgotPasswordRoute(
    viewModel: ForgotPasswordViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        state = state,
        onEmailChange = { viewModel.sendIntent(ForgotPasswordIntent.EmailChanged(it)) },
        onSubmit = { viewModel.sendIntent(ForgotPasswordIntent.Submit) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
