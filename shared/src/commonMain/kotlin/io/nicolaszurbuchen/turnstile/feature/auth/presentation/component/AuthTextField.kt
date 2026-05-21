package io.nicolaszurbuchen.turnstile.feature.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation =
        if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }

    Column(modifier = modifier) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (isError) turnstileColors.danger else turnstileColors.surface,
                        shape = RoundedCornerShape(12.dp),
                    ).padding(horizontal = spacing.md, vertical = 14.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (isError) turnstileColors.danger else turnstileColors.textSecondary,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(12.dp))
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(text = hint, color = turnstileColors.textTertiary, fontSize = 15.sp)
                    }
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        visualTransformation = visualTransformation,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        singleLine = true,
                        textStyle =
                            LocalTextStyle.current.copy(
                                fontSize = 15.sp,
                                color = turnstileColors.textPrimary,
                            ),
                        cursorBrush = SolidColor(turnstileColors.textPrimary),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                if (isPassword) {
                    Spacer(Modifier.width(spacing.sm))
                    Icon(
                        imageVector =
                            if (passwordVisible) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                        contentDescription = null,
                        tint = turnstileColors.textSecondary,
                        modifier =
                            Modifier
                                .size(20.dp)
                                .clickable { passwordVisible = !passwordVisible },
                    )
                }
            }
        }
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = turnstileColors.danger,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = spacing.xs, top = spacing.xs),
            )
        }
    }
}
