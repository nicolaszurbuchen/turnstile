package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    ForgotPasswordScreen(
        state = state,
        onEmailChange = { viewModel.sendIntent(ForgotPasswordIntent.EmailChanged(it)) },
        onSubmit = { viewModel.sendIntent(ForgotPasswordIntent.Submit) },
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
