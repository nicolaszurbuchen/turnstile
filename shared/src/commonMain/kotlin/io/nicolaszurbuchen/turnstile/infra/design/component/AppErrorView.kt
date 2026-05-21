package io.nicolaszurbuchen.turnstile.infra.design.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors

@Composable
fun AppErrorView(
    message: String,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.CloudOff,
            contentDescription = null,
            tint = turnstileColors.textTertiary,
            modifier = Modifier.size(52.dp),
        )
        Spacer(Modifier.height(spacing.md))
        Text(
            text = "Something went wrong",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = turnstileColors.textPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(spacing.xs))
        Text(
            text = message,
            fontSize = 14.sp,
            color = turnstileColors.textSecondary,
            textAlign = TextAlign.Center,
        )
        if (onRetry != null) {
            Spacer(Modifier.height(spacing.lg))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = turnstileColors.accent),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Try again",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = turnstileColors.onAccent,
                )
            }
        }
    }
}
