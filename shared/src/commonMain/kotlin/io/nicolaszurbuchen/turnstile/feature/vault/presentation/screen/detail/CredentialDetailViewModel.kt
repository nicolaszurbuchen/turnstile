package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.DeleteCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation.DetailDestination
import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import io.nicolaszurbuchen.turnstile.infra.ui.Loadable
import kotlinx.coroutines.launch

class CredentialDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getCredential: GetCredentialUseCase,
    private val deleteCredential: DeleteCredentialUseCase,
) : MviViewModel<Loadable<CredentialDetailState>, CredentialDetailTrigger, CredentialDetailIntent, CredentialDetailAction, CredentialDetailCommand, CredentialDetailEvent>(
        initialState = Loadable.Loading,
        reducer = CredentialDetailReducer,
    ) {

    init {
        val credentialId = savedStateHandle.toRoute<DetailDestination>().id
        viewModelScope.launch { executeCommand(CredentialDetailCommand.LoadCredential(credentialId)) }
    }

    override suspend fun executeCommand(command: CredentialDetailCommand) {
        when (command) {
            is CredentialDetailCommand.LoadCredential -> loadCredential(command.id)
            is CredentialDetailCommand.DeleteCredential -> delete(command.id)
        }
    }

    private suspend fun loadCredential(id: String) {
        runCatching { getCredential(id) }
            .onSuccess { credential ->
                if (credential != null) {
                    dispatchAction(CredentialDetailAction.CredentialLoaded(credential))
                } else {
                    dispatchAction(CredentialDetailAction.LoadFailed(AppError("Credential not found")))
                }
            }
            .onFailure { dispatchAction(CredentialDetailAction.LoadFailed(AppError(it.message ?: "Unknown error", it))) }
    }

    private suspend fun delete(id: String) {
        runCatching { deleteCredential(id) }
            .onSuccess { dispatchAction(CredentialDetailAction.Deleted) }
            .onFailure { dispatchAction(CredentialDetailAction.DeleteFailed(AppError(it.message ?: "Unknown error", it))) }
    }
}
