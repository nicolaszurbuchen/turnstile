package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

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
import androidx.compose.material.icons.filled.Person
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
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthBackButton
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthHeading
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthModalView
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthSocialSection
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthTextField
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstilePrimaryButton
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.UiText
import org.jetbrains.compose.resources.stringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_sign_in
import turnstile.shared.generated.resources.auth_sign_up
import turnstile.shared.generated.resources.auth_signup_has_account
import turnstile.shared.generated.resources.auth_signup_subtitle
import turnstile.shared.generated.resources.auth_signup_title
import turnstile.shared.generated.resources.auth_username
import turnstile.shared.generated.resources.common_email
import turnstile.shared.generated.resources.common_password

@Composable
fun SignUpScreen(
    state: SignUpState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onSignInClick: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailFocus = FocusRequester()
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
            modifier =
                Modifier
                    .weight(1f),
        )
        AuthModalView {
            AuthHeading(
                title = UiText.Resource(Res.string.auth_signup_title),
                subtitle = UiText.Resource(Res.string.auth_signup_subtitle),
            )
            Spacer(Modifier.height(28.dp))

            AuthTextField(
                value = state.username,
                onValueChange = onUsernameChange,
                hint = stringResource(Res.string.auth_username),
                leadingIcon = Icons.Filled.Person,
                isError = state.usernameError != null,
                errorMessage = state.usernameError?.let { stringResource(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { emailFocus.requestFocus() }),
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))

            AuthTextField(
                value = state.email,
                onValueChange = onEmailChange,
                hint = stringResource(Res.string.common_email),
                leadingIcon = Icons.Filled.Email,
                isError = state.emailError != null,
                errorMessage = state.emailError?.let { stringResource(it) },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onNext = { passwordFocus.requestFocus() },
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .focusRequester(emailFocus),
            )
            Spacer(Modifier.height(12.dp))

            AuthTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                hint = stringResource(Res.string.common_password),
                leadingIcon = Icons.Filled.Lock,
                isError = state.passwordError != null,
                errorMessage = state.passwordError?.let { stringResource(it) },
                isPassword = true,
                keyboardOptions =
                    KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = { focusManager.clearFocus() },
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocus),
            )

            state.submitError?.let { error ->
                Spacer(Modifier.height(spacing.sm))
                Text(text = error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(Modifier.height(spacing.lg))

            TurnstilePrimaryButton(
                text = UiText.Resource(id = Res.string.auth_sign_up),
                onClick = onSubmit,
                enabled = state.canSubmit,
                loading = state.loading,
            )

            Spacer(Modifier.height(spacing.lg))
            AuthSocialSection()
            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.auth_signup_has_account),
                    fontSize = 14.sp,
                    color = turnstileColors.textSecondary,
                )
                TextButton(
                    onClick = onSignInClick,
                ) {
                    Text(
                        text = stringResource(Res.string.auth_sign_in),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = turnstileColors.accent,
                    )
                }
            }
        }
    }
}
