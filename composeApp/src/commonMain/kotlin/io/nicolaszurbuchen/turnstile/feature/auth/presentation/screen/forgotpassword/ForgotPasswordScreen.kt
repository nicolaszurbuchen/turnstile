package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.jetbrains.compose.resources.stringResource
import turnstile.composeapp.generated.resources.Res
import turnstile.composeapp.generated.resources.auth_forgot_back_to_signin
import turnstile.composeapp.generated.resources.auth_forgot_password
import turnstile.composeapp.generated.resources.auth_forgot_submit
import turnstile.composeapp.generated.resources.auth_forgot_subtitle
import turnstile.composeapp.generated.resources.auth_forgot_success_body
import turnstile.composeapp.generated.resources.auth_forgot_success_title
import turnstile.composeapp.generated.resources.common_back
import turnstile.composeapp.generated.resources.common_email

private val ButtonDark = Color(0xFF2C2C2E)
private val LabelGrey = Color(0xFF8E8E93)
private val FieldGrey = Color(0xFFF0F0F3)

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onEmailChanged: (String) -> Unit,
    onSubmitted: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
}

@Composable
private fun FormContent(
    state: ForgotPasswordState,
    onEmailChanged: (String) -> Unit,
    onSubmitted: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = stringResource(Res.string.auth_forgot_password),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1C1C1E),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = stringResource(Res.string.auth_forgot_subtitle),
        fontSize = 14.sp,
        color = LabelGrey,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
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
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus(); onSubmitted() }
        ),
        modifier = Modifier.fillMaxWidth(),
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
                text = stringResource(Res.string.auth_forgot_submit),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun SuccessContent(onNavigateBack: () -> Unit) {
    Text(
        text = stringResource(Res.string.auth_forgot_success_title),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1C1C1E),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(16.dp))
    Text(
        text = stringResource(Res.string.auth_forgot_success_body),
        fontSize = 15.sp,
        color = LabelGrey,
        lineHeight = 22.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(32.dp))
    Button(
        onClick = onNavigateBack,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ButtonDark),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = stringResource(Res.string.auth_forgot_back_to_signin),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}
