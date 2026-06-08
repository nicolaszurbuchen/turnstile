package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val clipboardManager = LocalClipboardManager.current

    val onNavigateToDetailUpdated by rememberUpdatedState(onNavigateToDetail)
    val onNavigateToCreateUpdated by rememberUpdatedState(onNavigateToCreate)
    val onNavigateToAuthUpdated by rememberUpdatedState(onNavigateToAuth)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                is CredentialListLabel.NavigateToDetail -> onNavigateToDetailUpdated(label.id)
                CredentialListLabel.NavigateToCreate -> onNavigateToCreateUpdated()
                CredentialListLabel.NavigateToAuth -> onNavigateToAuthUpdated()
            }
        }
    }

    CredentialListScreen(
        state = state,
        onEntryClick = { id -> viewModel.onIntent(CredentialListIntent.EntryClicked(id)) },
        onCopyUsername = { username ->
            clipboardManager.setText(AnnotatedString(username))
        },
        onCopyPassword = { password ->
            clipboardManager.setText(AnnotatedString(password))
        },
        onCreateClick = { viewModel.onIntent(CredentialListIntent.CreateClicked) },
        onSignOutClick = { viewModel.onIntent(CredentialListIntent.SignOutClicked) },
        onRetryInitialLoad = { viewModel.onIntent(CredentialListIntent.RetryInitialLoad) },
        onDismissStreamError = { viewModel.onIntent(CredentialListIntent.DismissStreamError) },
        modifier = modifier,
    )
}
