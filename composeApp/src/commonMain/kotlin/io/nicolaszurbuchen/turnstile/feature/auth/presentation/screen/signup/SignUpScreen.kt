package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthBackground
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthTextField
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.InvertedArchShape
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import turnstile.composeapp.generated.resources.Res
import turnstile.composeapp.generated.resources.auth_full_name
import turnstile.composeapp.generated.resources.auth_or_continue_with
import turnstile.composeapp.generated.resources.auth_sign_in
import turnstile.composeapp.generated.resources.auth_sign_up
import turnstile.composeapp.generated.resources.auth_signup_has_account
import turnstile.composeapp.generated.resources.auth_signup_subtitle
import turnstile.composeapp.generated.resources.auth_signup_title
import turnstile.composeapp.generated.resources.common_back
import turnstile.composeapp.generated.resources.common_email
import turnstile.composeapp.generated.resources.common_password
import turnstile.composeapp.generated.resources.ic_apple
import turnstile.composeapp.generated.resources.ic_google

private val ButtonDark = Color(0xFF2C2C2E)
private val LabelGrey = Color(0xFF8E8E93)
private val DividerColor = Color(0xFFE5E5EA)
private val FieldGrey = Color(0xFFF0F0F3)

@Composable
fun SignUpScreen(
    state: SignUpState,
    onFullNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSubmitted: () -> Unit,
    onSignInClicked: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailFocus = FocusRequester()
    val passwordFocus = FocusRequester()
    val focusManager = LocalFocusManager.current

    AuthBackground(modifier) {
        FilledIconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = FieldGrey,
                contentColor = Color(0xFF1C1C1E),
            ),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.common_back),
                modifier = Modifier.size(20.dp),
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.78f),
            shape = InvertedArchShape(72.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp)
                    .padding(top = 88.dp)
                    .navigationBarsPadding()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(Res.string.auth_signup_title),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1E),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(Res.string.auth_signup_subtitle),
                    fontSize = 14.sp,
                    color = LabelGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(28.dp))

                AuthTextField(
                    value = state.fullName,
                    onValueChange = onFullNameChanged,
                    hint = stringResource(Res.string.auth_full_name),
                    leadingIcon = Icons.Filled.Person,
                    isError = state.fullNameError != null,
                    errorMessage = state.fullNameError?.let { stringResource(it) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { emailFocus.requestFocus() }),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(12.dp))

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(emailFocus),
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
                    Spacer(Modifier.height(8.dp))
                    Text(text = error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = onSubmitted,
                    enabled = state.canSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonDark),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (state.loading) {
                        CircularProgressIndicator(modifier = Modifier.height(20.dp), color = Color.White)
                    } else {
                        Text(
                            text = stringResource(Res.string.auth_sign_up),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
                SocialSection()
                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = stringResource(Res.string.auth_signup_has_account), fontSize = 14.sp, color = LabelGrey)
                    TextButton(onClick = onSignInClicked) {
                        Text(
                            text = stringResource(Res.string.auth_sign_in),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ButtonDark,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SocialSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
        Text(
            text = stringResource(Res.string.auth_or_continue_with),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 12.sp,
            color = LabelGrey,
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
    }
    Spacer(Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        SocialIconButton(
            iconRes = Res.drawable.ic_google,
            contentDescription = "Continue with Google",
            tintColor = Color.Unspecified,
        )
        Spacer(Modifier.width(20.dp))
        SocialIconButton(
            iconRes = Res.drawable.ic_apple,
            contentDescription = "Continue with Apple",
            tintColor = Color(0xFF1C1C1E),
        )
    }
}

@Composable
private fun SocialIconButton(
    iconRes: org.jetbrains.compose.resources.DrawableResource,
    contentDescription: String,
    tintColor: Color,
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .border(1.dp, DividerColor, CircleShape)
            .clip(CircleShape)
            .clickable { },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = tintColor,
        )
    }
}
