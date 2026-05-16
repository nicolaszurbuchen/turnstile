package io.nicolaszurbuchen.turnstile.feature.auth.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AuthContainer(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        AuthBackground()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            content()
        }
    }
}
