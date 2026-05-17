package io.nicolaszurbuchen.turnstile.core.design.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.core.design.theme.spacing
import io.nicolaszurbuchen.turnstile.core.design.theme.turnstileColors

enum class BannerStyle { Card }

enum class BannerSeverity { Error, Warning, Success }

@Composable
fun AppBanner(
    message: String,
    style: BannerStyle = BannerStyle.Card,
    severity: BannerSeverity = BannerSeverity.Error,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    val containerColor =
        when (severity) {
            BannerSeverity.Error -> turnstileColors.danger
            BannerSeverity.Warning -> turnstileColors.warning.copy(alpha = 0.15f)
            BannerSeverity.Success -> turnstileColors.success.copy(alpha = 0.15f)
        }
    val contentColor =
        when (severity) {
            BannerSeverity.Error -> turnstileColors.danger
            BannerSeverity.Warning -> turnstileColors.warning
            BannerSeverity.Success -> turnstileColors.success
        }
    val icon: ImageVector =
        when (severity) {
            BannerSeverity.Error -> Icons.Outlined.CloudOff
            BannerSeverity.Warning -> Icons.Default.Warning
            BannerSeverity.Success -> Icons.Default.CheckCircle
        }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.md, vertical = spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(spacing.sm))
            Text(
                text = message,
                fontSize = 13.sp,
                color = contentColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction) {
                    Text(
                        text = actionLabel,
                        fontSize = 13.sp,
                        color = contentColor,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            if (onDismiss != null) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = contentColor,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}
