package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthBackButton
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthModalView
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword.component.FormContent
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword.component.SuccessContent

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onEmailChanged: (String) -> Unit,
    onSubmitted: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AuthBackButton(
            onNavigateBack = onNavigateBack,
        )
        Spacer(
            modifier = Modifier
                .weight(1f),
        )
        AuthModalView {
            if (state.submitted) {
                SuccessContent(onNavigateBack = onNavigateBack)
            } else {
                FormContent(
                    state = state,
                    onEmailChanged = onEmailChanged,
                    onSubmitted = onSubmitted,
                )
            }
        }
    }
}
