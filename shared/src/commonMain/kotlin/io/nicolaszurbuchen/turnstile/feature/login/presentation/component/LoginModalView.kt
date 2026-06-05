package io.nicolaszurbuchen.turnstile.feature.login.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors

@Composable
fun LoginModalView(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        shape = InvertedArchShape(72.dp),
        color = MaterialTheme.turnstileColors.surface,
        modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = MaterialTheme.spacing.xl)
                    .padding(top = 88.dp)
                    .navigationBarsPadding()
                    .padding(bottom = MaterialTheme.spacing.xl),
        ) {
            content()
        }
    }
}

private class InvertedArchShape(
    private val archDepth: Dp = 72.dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val depth = with(density) { archDepth.toPx() }
        val path =
            Path().apply {
                moveTo(0f, 0f)
                cubicTo(
                    x1 = size.width * 0.3f,
                    y1 = depth,
                    x2 = size.width * 0.7f,
                    y2 = depth,
                    x3 = size.width,
                    y3 = 0f,
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
        return Outline.Generic(path)
    }
}
