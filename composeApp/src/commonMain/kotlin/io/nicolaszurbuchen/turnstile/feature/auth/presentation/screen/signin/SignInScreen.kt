package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.core.design.component.TurnstilePrimaryButton
import io.nicolaszurbuchen.turnstile.core.design.theme.spacing
import io.nicolaszurbuchen.turnstile.core.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.core.ui.UiText
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthBackButton
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthHeading
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthModalView
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthSocialSection
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthTextField
import org.jetbrains.compose.resources.stringResource
import turnstile.composeapp.generated.resources.Res
import turnstile.composeapp.generated.resources.auth_forgot_password
import turnstile.composeapp.generated.resources.auth_sign_up
import turnstile.composeapp.generated.resources.auth_signin_no_account
import turnstile.composeapp.generated.resources.auth_signin_remember_me
import turnstile.composeapp.generated.resources.auth_signin_submit
import turnstile.composeapp.generated.resources.auth_signin_subtitle
import turnstile.composeapp.generated.resources.auth_signin_title
import turnstile.composeapp.generated.resources.common_email
import turnstile.composeapp.generated.resources.common_password

@Composable
fun SignInScreen(
    state: SignInState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRememberMeToggled: () -> Unit,
    onSubmitted: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val passwordFocus = FocusRequester()
    val focusManager = LocalFocusManager.current
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier,
    ) {
        AuthBackButton(
            onNavigateBack = onNavigateBack,
        )
        Spacer(
            modifier = Modifier
                .weight(1f),
        )
        AuthModalView {
            AuthHeading(
                title = UiText.Resource(Res.string.auth_signin_title),
                subtitle = UiText.Resource(Res.string.auth_signin_subtitle),
            )
            Spacer(Modifier.height(28.dp))

            AuthTextField(
                value = state.email,
                onValueChange = onEmailChanged,
                hint = stringResource(Res.string.common_email),
                leadingIcon = Icons.Filled.Email,
                isError = state.emailError != null,
                errorMessage = state.emailError?.let { stringResource(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))

            AuthTextField(
                value = state.password,
                onValueChange = onPasswordChanged,
                hint = stringResource(Res.string.common_password),
                leadingIcon = Icons.Filled.Lock,
                isError = state.passwordError != null,
                errorMessage = state.passwordError?.let { stringResource(it) },
                isPassword = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus(); onSubmitted() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocus),
            )

            state.submitError?.let { error ->
                Spacer(Modifier.height(spacing.sm))
                Text(text = error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(Modifier.height(spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.rememberMe,
                        onCheckedChange = { onRememberMeToggled() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = turnstileColors.accent,
                            checkmarkColor = turnstileColors.onAccent,
                        ),
                    )
                    Text(text = stringResource(Res.string.auth_signin_remember_me), fontSize = 13.sp, color = turnstileColors.textSecondary)
                }
                TextButton(onClick = onForgotPasswordClicked) {
                    Text(text = stringResource(Res.string.auth_forgot_password), fontSize = 13.sp, color = turnstileColors.textSecondary)
                }
            }

            Spacer(Modifier.height(spacing.md))

            TurnstilePrimaryButton(
                text = UiText.Resource(id = Res.string.auth_signin_submit),
                onClick = onSubmitted,
                enabled = state.canSubmit,
                loading = state.loading,
            )

            Spacer(Modifier.height(spacing.lg))
            AuthSocialSection()
            Spacer(Modifier.height(spacing.lg))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.auth_signin_no_account), fontSize = 14.sp, color = turnstileColors.textSecondary)
                TextButton(onClick = onSignUpClicked) {
                    Text(
                        text = stringResource(Res.string.auth_sign_up),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = turnstileColors.accent,
                    )
                }
            }
        }
    }
}
