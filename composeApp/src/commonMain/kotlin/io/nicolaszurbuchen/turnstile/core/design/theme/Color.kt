package io.nicolaszurbuchen.turnstile.core.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Turnstile semantic color tokens.
 *
 * Never reference palette values directly in composables — always go
 * through TurnstileColors. This ensures dark/light mode swaps correctly
 * and makes color intent readable at call sites.
 *
 * Access via MaterialTheme.colors inside a TurnstileTheme composable.
 */
data class TurnstileColors(

    // --- Surfaces ---
    val background: Color,
    val surface: Color,
    val surfaceRaised: Color,
    val inputBackground: Color,

    // --- Borders ---
    val borderSubtle: Color,
    val borderDefault: Color,

    // --- Text ---
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val textInverse: Color,

    // --- Accent (amber) ---
    val accent: Color,
    val onAccent: Color,
    val accentSubtle: Color,
    val onAccentSubtle: Color,

    // --- Success (green) ---
    val success: Color,
    val onSuccess: Color,
    val successSubtle: Color,
    val onSuccessSubtle: Color,

    // --- Danger (red) ---
    val danger: Color,
    val onDanger: Color,
    val dangerSubtle: Color,
    val onDangerSubtle: Color,

    // --- Warning (orange) ---
    val warning: Color,
    val onWarning: Color,
    val warningSubtle: Color,
    val onWarningSubtle: Color,

    // --- Mode flag ---
    val isDark: Boolean,
)

val DarkTurnstileColors = TurnstileColors(
    isDark = true,

    // Surfaces
    background = CharcoalPalette.charcoal950,
    surface = CharcoalPalette.charcoal800,
    surfaceRaised = CharcoalPalette.charcoal700,
    inputBackground = CharcoalPalette.charcoal700,

    // Borders
    borderSubtle = CharcoalPalette.charcoal700,
    borderDefault = CharcoalPalette.charcoal600,

    // Text
    textPrimary = CharcoalPalette.charcoal50,
    textSecondary = CharcoalPalette.charcoal300,
    textTertiary = CharcoalPalette.charcoal400,
    textDisabled = CharcoalPalette.charcoal500,
    textInverse = CharcoalPalette.charcoal900,

    // Accent
    accent = AmberPalette.amber500,
    onAccent = CharcoalPalette.charcoal50,
    accentSubtle = AmberPalette.amber900,
    onAccentSubtle = AmberPalette.amber400,

    // Success
    success = GreenPalette.green500,
    onSuccess = GreenPalette.green50,
    successSubtle = GreenPalette.green900,
    onSuccessSubtle = GreenPalette.green400,

    // Danger
    danger = RedPalette.red500,
    onDanger = RedPalette.red50,
    dangerSubtle = RedPalette.red900,
    onDangerSubtle = RedPalette.red400,

    // Warning
    warning = OrangePalette.orange500,
    onWarning = OrangePalette.orange50,
    warningSubtle = OrangePalette.orange900,
    onWarningSubtle = OrangePalette.orange400,
)

val LightTurnstileColors = TurnstileColors(
    isDark = false,

    // Surfaces
    background = Color(0xFFF5F3EF),
    surface = Color(0xFFFFFFFF),
    surfaceRaised = Color(0xFFF0EDE8),
    inputBackground = Color(0xFFF0EDE8),

    // Borders
    borderSubtle = CharcoalPalette.charcoal100,
    borderDefault = CharcoalPalette.charcoal200,

    // Text
    textPrimary = CharcoalPalette.charcoal900,
    textSecondary = CharcoalPalette.charcoal500,
    textTertiary = CharcoalPalette.charcoal400,
    textDisabled = CharcoalPalette.charcoal300,
    textInverse = CharcoalPalette.charcoal50,

    // Accent — same amber500 in both modes; step down text for light contrast
    accent = AmberPalette.amber500,
    onAccent = CharcoalPalette.charcoal50,
    accentSubtle = AmberPalette.amber200,
    onAccentSubtle = AmberPalette.amber800,

    // Success — step up to 600 for contrast on light bg
    success = GreenPalette.green600,
    onSuccess = GreenPalette.green50,
    successSubtle = GreenPalette.green200,
    onSuccessSubtle = GreenPalette.green800,

    // Danger
    danger = RedPalette.red600,
    onDanger = RedPalette.red50,
    dangerSubtle = RedPalette.red200,
    onDangerSubtle = RedPalette.red800,

    // Warning
    warning = OrangePalette.orange600,
    onWarning = OrangePalette.orange50,
    warningSubtle = OrangePalette.orange200,
    onWarningSubtle = OrangePalette.orange800,
)

internal val LocalTurnstileColors = staticCompositionLocalOf { DarkTurnstileColors }

/**
 * Shorthand accessor inside TurnstileTheme:
 *
 *   MaterialTheme.colors.accent
 *   MaterialTheme.colors.textSecondary
 */
val MaterialTheme.colors: TurnstileColors
    @Composable
    @ReadOnlyComposable
    get() = LocalTurnstileColors.current
