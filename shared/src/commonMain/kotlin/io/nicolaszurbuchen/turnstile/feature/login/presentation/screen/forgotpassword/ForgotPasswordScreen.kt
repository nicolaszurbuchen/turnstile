package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginBackButton
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginModalView
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.component.FormContent
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.component.SuccessContent

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        LoginBackButton(
            onNavigateBack = onNavigateBack,
        )
        Spacer(
            modifier =
                Modifier
                    .weight(1f),
        )
        LoginModalView {
            if (state.submitted) {
                SuccessContent(onNavigateBack = onNavigateBack)
            } else {
                FormContent(
                    state = state,
                    onEmailChange = onEmailChange,
                    onSubmit = onSubmit,
                )
            }
        }
    }
}
