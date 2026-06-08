package io.nicolaszurbuchen.turnstile.infra.design.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.UiText
import io.nicolaszurbuchen.turnstile.infra.ui.asString

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
        shape = RoundedCornerShape(12.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp),
    ) {
        Text(
            text = text.asString(),
            fontSize = ButtonFontSize,
            fontWeight = FontWeight.Normal,
            letterSpacing = ButtonLetterSpacing,
        )
    }
}
