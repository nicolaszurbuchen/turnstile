package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

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
fun CredentialDetailRoute(
    onNavigateToEdit: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CredentialDetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val clipboardManager = LocalClipboardManager.current

    val onNavigateToEditUpdated by rememberUpdatedState(onNavigateToEdit)
    val onNavigateBackUpdated by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                is CredentialDetailLabel.NavigateToEdit -> onNavigateToEditUpdated(label.id)
                CredentialDetailLabel.NavigateBack -> onNavigateBackUpdated()
            }
        }
    }

    CredentialDetailScreen(
        state = state,
        onBackClick = { viewModel.onIntent(CredentialDetailIntent.BackClicked) },
        onEditClick = { viewModel.onIntent(CredentialDetailIntent.EditClicked) },
        onDeleteClick = { viewModel.onIntent(CredentialDetailIntent.DeleteClicked) },
        onCopyUsername = { username ->
            clipboardManager.setText(AnnotatedString(username))
        },
        onCopyPassword = { password ->
            clipboardManager.setText(AnnotatedString(password))
        },
        onRetry = { viewModel.onIntent(CredentialDetailIntent.Retry) },
        modifier = modifier,
    )
}
