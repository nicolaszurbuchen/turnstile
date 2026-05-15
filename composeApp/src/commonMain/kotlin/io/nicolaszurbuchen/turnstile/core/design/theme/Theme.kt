package io.nicolaszurbuchen.turnstile.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightMaterialColorScheme = lightColorScheme(
    primary = AmberPalette.amber500,
    onPrimary = CharcoalPalette.charcoal50,
    primaryContainer = AmberPalette.amber100,
    onPrimaryContainer = AmberPalette.amber900,
    background = CharcoalPalette.charcoal50,
    onBackground = CharcoalPalette.charcoal900,
    surface = CharcoalPalette.charcoal50,
    onSurface = CharcoalPalette.charcoal900,
    surfaceVariant = CharcoalPalette.charcoal100,
    onSurfaceVariant = CharcoalPalette.charcoal500,
    outline = CharcoalPalette.charcoal200,
    outlineVariant = CharcoalPalette.charcoal100,
    error = RedPalette.red600,
    onError = RedPalette.red50,
    errorContainer = RedPalette.red100,
    onErrorContainer = RedPalette.red800,
)

private val DarkMaterialColorScheme = darkColorScheme(
    primary = AmberPalette.amber500,
    onPrimary = CharcoalPalette.charcoal50,
    primaryContainer = AmberPalette.amber900,
    onPrimaryContainer = AmberPalette.amber200,
    background = CharcoalPalette.charcoal950,
    onBackground = CharcoalPalette.charcoal50,
    surface = CharcoalPalette.charcoal800,
    onSurface = CharcoalPalette.charcoal50,
    surfaceVariant = CharcoalPalette.charcoal700,
    onSurfaceVariant = CharcoalPalette.charcoal300,
    outline = CharcoalPalette.charcoal600,
    outlineVariant = CharcoalPalette.charcoal700,
    error = RedPalette.red500,
    onError = RedPalette.red50,
    errorContainer = RedPalette.red900,
    onErrorContainer = RedPalette.red200,
)

@Composable
fun TurnstileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val turnstileColors = if (darkTheme) DarkTurnstileColors else LightTurnstileColors
    val materialColors = if (darkTheme) DarkMaterialColorScheme else LightMaterialColorScheme

    MaterialTheme(
        colorScheme = materialColors,
        //typography = TurnstileTypography,
        shapes = TurnstileShapes,
        content = {
            CompositionLocalProvider(
                LocalSpacing provides Spacing(),
                LocalTurnstileColors provides turnstileColors,
            ) {
                content()
            }
        }
    )
}
