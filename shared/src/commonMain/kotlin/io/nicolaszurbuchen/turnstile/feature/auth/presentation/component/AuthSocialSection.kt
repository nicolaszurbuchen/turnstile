package io.nicolaszurbuchen.turnstile.feature.auth.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.core.design.theme.spacing
import io.nicolaszurbuchen.turnstile.core.design.theme.turnstileColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.auth_or_continue_with
import turnstile.shared.generated.resources.ic_apple
import turnstile.shared.generated.resources.ic_google

@Composable
fun AuthSocialSection(modifier: Modifier = Modifier) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = turnstileColors.borderSubtle)
            Text(
                text = stringResource(Res.string.auth_or_continue_with),
                modifier = Modifier.padding(horizontal = spacing.md),
                fontSize = 12.sp,
                color = turnstileColors.textSecondary,
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = turnstileColors.borderSubtle)
        }
        Spacer(Modifier.height(spacing.md))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            AuthSocialIconButton(
                iconRes = Res.drawable.ic_google,
                contentDescription = "Continue with Google",
                tintColor = turnstileColors.textPrimary,
            )
            Spacer(Modifier.width(20.dp))
            AuthSocialIconButton(
                iconRes = Res.drawable.ic_apple,
                contentDescription = "Continue with Apple",
                tintColor = turnstileColors.textPrimary,
            )
        }
    }
}

@Composable
private fun AuthSocialIconButton(
    iconRes: DrawableResource,
    contentDescription: String,
    tintColor: Color,
) {
    val turnstileColors = MaterialTheme.turnstileColors

    Box(
        modifier =
            Modifier
                .size(56.dp)
                .border(1.dp, turnstileColors.borderSubtle, CircleShape)
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
