package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

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
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginBackButton
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginHeading
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginModalView
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileTextField
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstilePrimaryButton
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.UiText
import org.jetbrains.compose.resources.stringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_forgot_password
import turnstile.shared.generated.resources.auth_sign_up
import turnstile.shared.generated.resources.auth_signin_no_account
import turnstile.shared.generated.resources.auth_signin_submit
import turnstile.shared.generated.resources.auth_signin_subtitle
import turnstile.shared.generated.resources.auth_signin_title
import turnstile.shared.generated.resources.common_email
import turnstile.shared.generated.resources.common_password

@Composable
fun SignInScreen(
    state: SignInState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val passwordFocus = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
    ) {
        LoginBackButton(onNavigateBack = onNavigateBack)
        Spacer(modifier = Modifier.weight(1f))

        LoginModalView {
            LoginHeading(
                title = UiText.Resource(Res.string.auth_signin_title),
                subtitle = UiText.Resource(Res.string.auth_signin_subtitle),
            )
            Spacer(Modifier.height(MaterialTheme.spacing.lg))

            TurnstileTextField(
                value = state.email,
                onValueChange = onEmailChanged,
                hint = stringResource(Res.string.common_email),
                leadingIcon = Icons.Filled.Email,
                isError = state.emailError != null,
                errorMessage = state.emailError?.let { stringResource(it) },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(MaterialTheme.spacing.md))

            TurnstileTextField(
                value = state.password,
                onValueChange = onPasswordChanged,
                hint = stringResource(Res.string.common_password),
                leadingIcon = Icons.Filled.Lock,
                isError = state.passwordError != null,
                errorMessage = state.passwordError?.let { stringResource(it) },
                isPassword = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocus),
            )

            state.submitError?.let { error ->
                Spacer(Modifier.height(MaterialTheme.spacing.sm))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                )
            }
            Spacer(Modifier.height(MaterialTheme.spacing.xs))

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextButton(onClick = onForgotPasswordClicked) {
                    Text(
                        text = stringResource(Res.string.auth_forgot_password),
                        fontSize = 12.sp,
                        color = MaterialTheme.turnstileColors.accent,
                    )
                }
            }
            Spacer(Modifier.weight(1f))

            TurnstilePrimaryButton(
                text = UiText.Resource(id = Res.string.auth_signin_submit),
                onClick = onSubmit,
                enabled = state.canSubmit,
                loading = state.loading,
            )
            Spacer(Modifier.height(MaterialTheme.spacing.lg))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(Res.string.auth_signin_no_account),
                    fontSize = 14.sp,
                    color = MaterialTheme.turnstileColors.textSecondary,
                )
                TextButton(onClick = onSignUpClicked) {
                    Text(
                        text = stringResource(Res.string.auth_sign_up),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.turnstileColors.accent,
                    )
                }
            }
        }
    }
}
