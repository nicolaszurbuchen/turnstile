package io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors

@Composable
fun DashboardSkeleton(modifier: Modifier = Modifier) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    val shimmerAlpha by rememberInfiniteTransition(label = "shimmer")
        .animateFloat(
            initialValue = 0.35f,
            targetValue = 0.85f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 900, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "shimmer-alpha",
        )

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = spacing.lg),
    ) {
        // Title placeholder
        Box(
            modifier =
                Modifier
                    .padding(horizontal = spacing.md)
                    .width(160.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(turnstileColors.surface.copy(alpha = shimmerAlpha)),
        )
        Spacer(Modifier.height(spacing.lg))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(8) {
                SkeletonItem(
                    shimmerAlpha = shimmerAlpha,
                    primaryWidth =
                        if (it % 3 == 0) {
                            0.55f
                        } else if (it % 3 == 1) {
                            0.65f
                        } else {
                            0.50f
                        },
                    secondaryWidth = if (it % 2 == 0) 0.40f else 0.35f,
                )
            }
        }
    }
}

@Composable
private fun SkeletonItem(
    shimmerAlpha: Float,
    primaryWidth: Float,
    secondaryWidth: Float,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.md, vertical = spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(turnstileColors.surface.copy(alpha = shimmerAlpha)),
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(primaryWidth)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(turnstileColors.surface.copy(alpha = shimmerAlpha)),
            )
            Spacer(Modifier.height(6.dp))
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(secondaryWidth)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(turnstileColors.surface.copy(alpha = shimmerAlpha * 0.7f)),
            )
        }
    }
}
