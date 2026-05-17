package io.nicolaszurbuchen.turnstile

import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.turnstile.core.design.theme.TurnstileTheme
import io.nicolaszurbuchen.turnstile.core.navigation.NavGraph

@Composable
fun App() {
    TurnstileTheme {
        NavGraph()
    }
}
