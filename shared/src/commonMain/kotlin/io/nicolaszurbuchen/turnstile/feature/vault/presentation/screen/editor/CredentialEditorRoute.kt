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
    viewModel: CredentialEditorViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onNavigateBackUpdated by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(Unit) {
        viewModel.labels.collect { label ->
            when (label) {
                CredentialEditorLabel.NavigateBack -> {
                    onNavigateBackUpdated()
                }

                is CredentialEditorLabel.ShowError -> {
                    // TODO: This could be a toast or snackbar
                }
            }
        }
    }

    CredentialEditorScreen(
        state = state,
        onTitleChange = { viewModel.onIntent(CredentialEditorIntent.TitleChanged(it)) },
        onUsernameChange = { viewModel.onIntent(CredentialEditorIntent.UsernameChanged(it)) },
        onPasswordChange = { viewModel.onIntent(CredentialEditorIntent.PasswordChanged(it)) },
        onMemoChange = { viewModel.onIntent(CredentialEditorIntent.MemoChanged(it)) },
        onSaveClick = { viewModel.onIntent(CredentialEditorIntent.SaveClicked) },
        onCancelClick = { viewModel.onIntent(CredentialEditorIntent.CancelClicked) },
        onRetry = { viewModel.onIntent(CredentialEditorIntent.Retry) },
        onDismissSaveError = { viewModel.onIntent(CredentialEditorIntent.DismissSaveError) },
        modifier = modifier,
    )
}
