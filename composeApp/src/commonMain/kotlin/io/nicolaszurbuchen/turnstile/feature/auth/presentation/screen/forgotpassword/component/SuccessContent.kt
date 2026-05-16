package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.core.design.theme.spacing
import io.nicolaszurbuchen.turnstile.core.design.theme.turnstileColors
import org.jetbrains.compose.resources.stringResource
import turnstile.composeapp.generated.resources.Res
import turnstile.composeapp.generated.resources.auth_forgot_back_to_signin
import turnstile.composeapp.generated.resources.auth_forgot_success_body
import turnstile.composeapp.generated.resources.auth_forgot_success_title

@Composable
internal fun SuccessContent(
    onNavigateBack: () -> Unit,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Text(
        text = stringResource(Res.string.auth_forgot_success_title),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = turnstileColors.textPrimary,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(spacing.md))
    Text(
        text = stringResource(Res.string.auth_forgot_success_body),
        fontSize = 15.sp,
        color = turnstileColors.textSecondary,
        lineHeight = 22.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(spacing.xl))
    Button(
        onClick = onNavigateBack,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = turnstileColors.accent),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = stringResource(Res.string.auth_forgot_back_to_signin),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = turnstileColors.onAccent,
            textAlign = TextAlign.Center,
        )
    }
}
