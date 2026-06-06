package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.SaveCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation.EditorDestination
import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import kotlinx.coroutines.launch

class CredentialEditorViewModel(
    savedStateHandle: SavedStateHandle,
    private val getCredential: GetCredentialUseCase,
    private val saveCredential: SaveCredentialUseCase,
) : MviViewModel<
        CredentialEditorStateImpl,
        CredentialEditorTrigger,
        CredentialEditorIntent,
        CredentialEditorAction,
        CredentialEditorCommand,
        CredentialEditorEvent,
    >(
        initialState = CredentialEditorStateImpl(),
        reducer = CredentialEditorReducer,
    ) {
    init {
        val credentialId = savedStateHandle.toRoute<EditorDestination>().id
        if (credentialId != null) {
            viewModelScope.launch {
                executeCommand(
                    CredentialEditorCommand.LoadCredential(
                        credentialId,
                    ),
                )
            }
        }
    }

    override suspend fun executeCommand(command: CredentialEditorCommand) {
        when (command) {
            is CredentialEditorCommand.LoadCredential -> loadCredential(command.id)
            is CredentialEditorCommand.SaveCredential -> save(command.credential)
        }
    }

    private suspend fun loadCredential(id: String) {
        runCatching { getCredential(id) }
            .onSuccess { credential ->
                if (credential != null) {
                    dispatchAction(CredentialEditorAction.CredentialLoaded(credential))
                }
            }
    }

    private suspend fun save(credential: Credential) {
        dispatchAction(CredentialEditorAction.Saving)
        runCatching { saveCredential(credential) }
            .onSuccess { dispatchAction(CredentialEditorAction.Saved) }
            .onFailure {
                dispatchAction(
                    CredentialEditorAction.SaveFailed(
                        AppError(
                            it.message ?: "Unknown error",
                            it,
                        ),
                    ),
                )
            }
    }
}
