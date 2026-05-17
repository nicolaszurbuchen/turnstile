package io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.turnstile.core.design.component.AppErrorView
import io.nicolaszurbuchen.turnstile.core.ui.Loadable

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel,
    onNavigateToDetail: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateToDetailUpdated by rememberUpdatedState(onNavigateToDetail)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is DashboardEvent.NavigateToDetail -> {
                    onNavigateToDetailUpdated(event.id)
                }

                is DashboardEvent.ShowDeleteError -> { /* TODO: surface snackbar / dialog */ }
            }
        }
    }

    when (val loadable = state) {
        is Loadable.Loading -> {
            DashboardSkeleton(modifier)
        }

        is Loadable.Failure -> {
            AppErrorView(
                message = loadable.error.message,
                onRetry = { viewModel.sendIntent(DashboardIntent.Retry) },
                modifier = modifier,
            )
        }

        is Loadable.Success -> {
            DashboardScreen(
                state = loadable.data,
                onRefresh = { viewModel.sendIntent(DashboardIntent.Refresh) },
                onDismissRefreshError = { viewModel.sendIntent(DashboardIntent.DismissRefreshError) },
                onEntryClick = { id -> viewModel.sendIntent(DashboardIntent.EntryClicked(id)) },
                onEntryDelete = { id -> viewModel.sendIntent(DashboardIntent.DeleteEntry(id)) },
                modifier = modifier,
            )
        }
    }
}
