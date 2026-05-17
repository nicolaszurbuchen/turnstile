package io.nicolaszurbuchen.turnstile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.nicolaszurbuchen.turnstile.core.design.theme.TurnstileTheme
import io.nicolaszurbuchen.turnstile.core.navigation.NavGraph

@Composable
@Preview
private fun AppPreview() {
    TurnstileTheme {
        NavGraph()
    }
}
