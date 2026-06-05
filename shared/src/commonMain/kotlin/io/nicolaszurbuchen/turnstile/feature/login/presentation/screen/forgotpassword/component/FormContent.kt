package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginTextField
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.ForgotPasswordState
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import org.jetbrains.compose.resources.stringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_forgot_password
import turnstile.shared.generated.resources.auth_forgot_submit
import turnstile.shared.generated.resources.auth_forgot_subtitle
import turnstile.shared.generated.resources.common_email

@Composable
internal fun FormContent(
    state: ForgotPasswordState,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.auth_forgot_password),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = turnstileColors.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(spacing.xs))
        Text(
            text = stringResource(Res.string.auth_forgot_subtitle),
            fontSize = 14.sp,
            color = turnstileColors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(28.dp))

        LoginTextField(
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
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onSubmit()
                    },
                ),
            modifier = Modifier.fillMaxWidth(),
        )

        state.submitError?.let { error ->
            Spacer(Modifier.height(spacing.sm))
            Text(text = error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }

        Spacer(Modifier.height(spacing.lg))

        Button(
            onClick = onSubmit,
            enabled = state.canSubmit,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = turnstileColors.accent),
            shape = RoundedCornerShape(12.dp),
        ) {
            if (state.loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = turnstileColors.onAccent)
            } else {
                Text(
                    text = stringResource(Res.string.auth_forgot_submit),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = turnstileColors.onAccent,
                )
            }
        }
    }
}
