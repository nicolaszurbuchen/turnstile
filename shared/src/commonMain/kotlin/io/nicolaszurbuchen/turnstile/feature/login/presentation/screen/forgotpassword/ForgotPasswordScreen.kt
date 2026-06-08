package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginBackButton
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginHeading
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginModalView
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstilePrimaryButton
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileTextField
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.ui.UiText
import org.jetbrains.compose.resources.stringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_forgot_back_to_signin
import turnstile.shared.generated.resources.auth_forgot_password
import turnstile.shared.generated.resources.auth_forgot_submit
import turnstile.shared.generated.resources.auth_forgot_subtitle
import turnstile.shared.generated.resources.auth_forgot_success_body
import turnstile.shared.generated.resources.auth_forgot_success_title
import turnstile.shared.generated.resources.common_email

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onEmailChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LoginBackButton(onNavigateBack = onNavigateBack)
        Spacer(modifier = Modifier.weight(1f))

        LoginModalView {
            if (state.submitted) {
                SuccessContent(onNavigateBack = onNavigateBack)
            } else {
                FormContent(
                    state = state,
                    onEmailChange = onEmailChanged,
                    onSubmit = onSubmit,
                )
            }
        }
    }
}

@Composable
private fun FormContent(
    state: ForgotPasswordState,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        LoginHeading(
            title = UiText.Resource(Res.string.auth_forgot_password),
            subtitle = UiText.Resource(Res.string.auth_forgot_subtitle),
        )
        Spacer(Modifier.height(MaterialTheme.spacing.lg))

        TurnstileTextField(
            value = state.email,
            onValueChange = onEmailChange,
            hint = stringResource(Res.string.common_email),
            leadingIcon = Icons.Filled.Email,
            isError = state.emailError != null,
            errorMessage = state.emailError?.let { stringResource(it) },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.fillMaxWidth(),
        )

        state.submitError?.let { error ->
            Spacer(Modifier.height(MaterialTheme.spacing.sm))
            Text(text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
            )
        }
        Spacer(Modifier.weight(1f))

        TurnstilePrimaryButton(
            text = UiText.Resource(id = Res.string.auth_forgot_submit),
            onClick = onSubmit,
            enabled = state.canSubmit,
            loading = state.loading,
        )
    }
}

@Composable
private fun SuccessContent(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LoginHeading(
            title = UiText.Resource(Res.string.auth_forgot_success_title),
            subtitle = UiText.Resource(Res.string.auth_forgot_success_body),
        )
        Spacer(Modifier.weight(1f))

        TurnstilePrimaryButton(
            text = UiText.Resource(id = Res.string.auth_forgot_back_to_signin),
            onClick = onNavigateBack,
        )
    }
}
