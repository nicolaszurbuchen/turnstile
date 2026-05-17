package io.nicolaszurbuchen.turnstile.core.design.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import io.nicolaszurbuchen.turnstile.core.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.core.ui.UiText
import io.nicolaszurbuchen.turnstile.core.ui.asString

/**
 * Secondary action button. Transparent fill, amber border and text.
 */
@Composable
fun TurnstileSecondaryButton(
    text: UiText,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors =
            ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.turnstileColors.accent,
            ),
        //        border = BorderStroke(
//            width = OutlineWidth,
//            color = if (enabled) MaterialTheme.turnstileColors.accent
//            else MaterialTheme.turnstileColors.borderDefault,
//        ),
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = text.asString(),
            fontSize = ButtonFontSize,
            fontWeight = FontWeight.Normal,
            letterSpacing = ButtonLetterSpacing,
        )
    }
}
