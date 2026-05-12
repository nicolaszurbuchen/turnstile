package io.nicolaszurbuchen.turnstile

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.nicolaszurbuchen.turnstile.core.navigation.NavGraph

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavGraph()
    }
}