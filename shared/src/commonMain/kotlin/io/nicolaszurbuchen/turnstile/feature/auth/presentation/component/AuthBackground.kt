package io.nicolaszurbuchen.turnstile.feature.auth.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.background2

@Composable
fun AuthBackground(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(Res.drawable.background2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxSize(),
        )
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops =
                                arrayOf(
                                    0.45f to Color.Transparent,
                                    0.8f to Color.Black.copy(alpha = 0.8f),
                                ),
                        ),
                    ),
        )
    }
}
