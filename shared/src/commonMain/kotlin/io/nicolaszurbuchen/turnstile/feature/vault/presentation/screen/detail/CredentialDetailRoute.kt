package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.nicolaszurbuchen.turnstile.infra.design.component.AppErrorView
import io.nicolaszurbuchen.turnstile.infra.ui.Loadable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CredentialDetailRoute(
    onNavigateToEdit: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<CredentialDetailViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateToEditUpdated by rememberUpdatedState(onNavigateToEdit)
    val onNavigateBackUpdated by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CredentialDetailEvent.NavigateToEdit -> onNavigateToEditUpdated(event.id)
                CredentialDetailEvent.NavigateBack -> onNavigateBackUpdated()
            }
        }
    }

    when (val loadable = state) {
        is Loadable.Loading -> {
            // TODO: Detail skeleton
        }

        is Loadable.Failure -> {
            AppErrorView(
                message = loadable.error.message,
                onRetry = { /* Re-load? */ },
                modifier = modifier,
            )
        }

        is Loadable.Success -> {
            CredentialDetailScreen(
                state = loadable.data,
                onBackClick = { viewModel.sendIntent(CredentialDetailIntent.BackClicked) },
                onEditClick = { viewModel.sendIntent(CredentialDetailIntent.EditClicked) },
                onDeleteClick = { viewModel.sendIntent(CredentialDetailIntent.DeleteClicked) },
                modifier = modifier,
            )
        }
    }
}
