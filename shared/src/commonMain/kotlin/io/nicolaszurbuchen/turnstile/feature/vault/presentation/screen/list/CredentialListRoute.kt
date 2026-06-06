package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

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
fun CredentialListRoute(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToAuth: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CredentialListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateToDetailUpdated by rememberUpdatedState(onNavigateToDetail)
    val onNavigateToCreateUpdated by rememberUpdatedState(onNavigateToCreate)
    val onNavigateToAuthUpdated by rememberUpdatedState(onNavigateToAuth)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CredentialListEvent.NavigateToDetail -> onNavigateToDetailUpdated(event.id)
                CredentialListEvent.NavigateToCreate -> onNavigateToCreateUpdated()
                CredentialListEvent.NavigateToAuth -> onNavigateToAuthUpdated()
            }
        }
    }

    when (val loadable = state) {
        is Loadable.Loading -> {
            // TODO: List skeleton
        }

        is Loadable.Failure -> {
            AppErrorView(
                message = loadable.error.message,
                onRetry = { /* Retry logic if needed */ },
                modifier = modifier,
            )
        }

        is Loadable.Success -> {
            CredentialListScreen(
                state = loadable.data,
                onEntryClick = { id -> viewModel.sendIntent(CredentialListIntent.EntryClicked(id)) },
                onCreateClick = { viewModel.sendIntent(CredentialListIntent.CreateClicked) },
                onSignOutClick = { viewModel.sendIntent(CredentialListIntent.SignOutClicked) },
                modifier = modifier,
            )
        }
    }
}
