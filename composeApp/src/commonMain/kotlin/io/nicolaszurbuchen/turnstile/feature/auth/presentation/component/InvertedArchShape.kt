package io.nicolaszurbuchen.turnstile.feature.auth.presentation.component

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class InvertedArchShape(private val archDepth: Dp = 72.dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val depth = with(density) { archDepth.toPx() }
        val path = Path().apply {
            moveTo(0f, 0f)
            cubicTo(
                x1 = size.width * 0.3f, y1 = depth,
                x2 = size.width * 0.7f, y2 = depth,
                x3 = size.width, y3 = 0f,
            )
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
