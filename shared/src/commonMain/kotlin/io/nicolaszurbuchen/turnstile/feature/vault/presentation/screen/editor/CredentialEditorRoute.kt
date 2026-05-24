package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CredentialEditorRoute(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<CredentialEditorViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onNavigateBackUpdated by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CredentialEditorEvent.NavigateBack -> onNavigateBackUpdated()
                is CredentialEditorEvent.ShowError -> { /* TODO: surface error */ }
            }
        }
    }

    CredentialEditorScreen(
        state = state,
        onTitleChange = { viewModel.sendIntent(CredentialEditorIntent.TitleChanged(it)) },
        onUsernameChange = { viewModel.sendIntent(CredentialEditorIntent.UsernameChanged(it)) },
        onPasswordChange = { viewModel.sendIntent(CredentialEditorIntent.PasswordChanged(it)) },
        onMemoChange = { viewModel.sendIntent(CredentialEditorIntent.MemoChanged(it)) },
        onSaveClick = { viewModel.sendIntent(CredentialEditorIntent.SaveClicked) },
        onCancelClick = { viewModel.sendIntent(CredentialEditorIntent.CancelClicked) },
        modifier = modifier,
    )
}
