package io.nicolaszurbuchen.turnstile.infra.design.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.UiText
import io.nicolaszurbuchen.turnstile.infra.ui.asString

/**
 * Primary action button. Amber fill, cream text.
 * Supports an in-button loading spinner — disables interaction while loading.
 */
@Composable
fun TurnstilePrimaryButton(
    text: UiText,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.turnstileColors.accent,
                contentColor = MaterialTheme.turnstileColors.onAccent,
            ),
        shape = RoundedCornerShape(12.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            this@Button.AnimatedVisibility(
                visible = !loading,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Text(
                    text = text.asString(),
                    fontSize = ButtonFontSize,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = ButtonLetterSpacing,
                )
            }
            this@Button.AnimatedVisibility(
                visible = loading,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.turnstileColors.onAccent,
                    strokeWidth = SpinnerStrokeWidth,
                    modifier =
                        Modifier
                            .size(SpinnerSize),
                )
            }
        }
    }
}

internal val ButtonHeight = 52.dp
internal val ButtonContentPadding = PaddingValues(horizontal = 24.dp)
internal val ButtonFontSize = 14.sp
internal val ButtonLetterSpacing = 0.01.sp
internal val OutlineWidth = 0.5.dp
internal val SpinnerSize = 20.dp
internal val SpinnerStrokeWidth = 2.dp
