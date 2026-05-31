package io.nicolaszurbuchen.turnstile.infra

import androidx.compose.runtime.Composable
import io.nicolaszurbuchen.turnstile.infra.design.theme.TurnstileTheme
import io.nicolaszurbuchen.turnstile.infra.navigation.NavGraph

@Composable
fun App() {
    TurnstileTheme {
        NavGraph()
    }
}
