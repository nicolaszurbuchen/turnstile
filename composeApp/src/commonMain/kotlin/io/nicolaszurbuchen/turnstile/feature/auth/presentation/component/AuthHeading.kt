package io.nicolaszurbuchen.turnstile.feature.auth.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.core.design.theme.spacing
import io.nicolaszurbuchen.turnstile.core.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.core.ui.UiText
import io.nicolaszurbuchen.turnstile.core.ui.asString

@Composable
fun AuthHeading(
    title: UiText,
    subtitle: UiText,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title.asString(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.turnstileColors.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacing.xs),
        )
        Text(
            text = subtitle.asString(),
            fontSize = 14.sp,
            color = MaterialTheme.turnstileColors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}