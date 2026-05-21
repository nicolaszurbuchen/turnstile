package io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.component.AppBanner
import io.nicolaszurbuchen.turnstile.infra.design.component.AppEmptyView
import io.nicolaszurbuchen.turnstile.infra.design.component.BannerSeverity
import io.nicolaszurbuchen.turnstile.infra.design.component.BannerStyle
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    onRefresh: () -> Unit,
    onDismissRefreshError: () -> Unit,
    onEntryClick: (String) -> Unit,
    onEntryDelete: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(turnstileColors.background)
                .statusBarsPadding(),
    ) {
        Text(
            text = "My Passwords",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = turnstileColors.textPrimary,
            modifier =
                Modifier
                    .padding(horizontal = spacing.md)
                    .padding(top = spacing.lg, bottom = spacing.md),
        )

        AnimatedVisibility(
            visible = state.refreshError != null,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
        ) {
            AppBanner(
                message = state.refreshError?.message.orEmpty(),
                style = BannerStyle.Card,
                severity = BannerSeverity.Error,
                actionLabel = "Retry",
                onAction = onRefresh,
                onDismiss = onDismissRefreshError,
                modifier =
                    Modifier
                        .padding(horizontal = spacing.md)
                        .padding(bottom = spacing.sm),
            )
        }

        PullToRefreshBox(
            isRefreshing = state.refreshing,
            onRefresh = onRefresh,
            modifier = Modifier.weight(1f),
        ) {
            if (state.isEmpty) {
                AppEmptyView(
                    title = "No passwords saved yet",
                    subtitle = "Tap + to add your first entry",
                )
            } else {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .navigationBarsPadding(),
                ) {
                    items(items = state.entries, key = { it.id }) { entry ->
                        CredentialItem(
                            entry = entry,
                            onClick = { onEntryClick(entry.id) },
                            onDelete = { onEntryDelete(entry.id) },
                            modifier = Modifier.animateItem(),
                        )
                    }
                    item { Spacer(Modifier.height(spacing.md)) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CredentialItem(
    entry: CredentialUiModel,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    val dismissState =
        rememberSwipeToDismissBoxState(
            confirmValueChange = { value ->
                if (value == SwipeToDismissBoxValue.EndToStart) {
                    onDelete()
                    true
                } else {
                    false
                }
            },
            positionalThreshold = { total -> total * 0.4f },
        )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = spacing.md, vertical = 4.dp)
                        .background(turnstileColors.danger, MaterialTheme.shapes.small),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = turnstileColors.onDanger,
                    modifier = Modifier.padding(end = spacing.md).size(22.dp),
                )
            }
        },
        modifier = modifier,
    ) {
        Card(
            onClick = onClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.md, vertical = 4.dp),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = turnstileColors.surface),
            border = BorderStroke(1.dp, turnstileColors.borderSubtle),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Row(
                modifier = Modifier.padding(spacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .background(turnstileColors.surface, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = entry.name.firstOrNull()?.uppercase() ?: "?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = turnstileColors.textSecondary,
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text = entry.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = turnstileColors.textPrimary,
                    )
                    Spacer(Modifier.height(spacing.xs))
                    Text(
                        text = entry.type,
                        fontSize = 13.sp,
                        color = turnstileColors.textSecondary,
                    )
                }
            }
        }
    }
}
