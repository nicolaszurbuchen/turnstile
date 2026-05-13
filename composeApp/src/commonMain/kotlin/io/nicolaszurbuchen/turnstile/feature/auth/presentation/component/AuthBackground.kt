package io.nicolaszurbuchen.turnstile.feature.auth.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import turnstile.composeapp.generated.resources.Res
import turnstile.composeapp.generated.resources.background

@Composable
fun AuthBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.45f to Color(0x22000000),
                            1.0f to Color(0xCC000000),
                        )
                    )
                )
        )
        // Only reserve space for the status bar at top — the card intentionally
        // extends behind the navigation bar at the bottom.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            content = content,
        )
    }
}
