package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.feature.auth.domain.usecase.SignOutUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialsUseCase
import io.nicolaszurbuchen.turnstile.infra.mvi.MviViewModel
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import io.nicolaszurbuchen.turnstile.infra.ui.Loadable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CredentialListViewModel(
    private val getCredentials: GetCredentialsUseCase,
    private val signOut: SignOutUseCase,
) : MviViewModel<Loadable<CredentialListStateImpl>, CredentialListTrigger, CredentialListIntent, CredentialListAction, CredentialListCommand, CredentialListEvent>(
        initialState = Loadable.Loading,
        reducer = CredentialListReducer,
    ) {
    init {
        viewModelScope.launch { executeCommand(CredentialListCommand.ObserveEntries) }
    }

    override suspend fun executeCommand(command: CredentialListCommand) {
        when (command) {
            CredentialListCommand.ObserveEntries -> observeEntries()
            CredentialListCommand.SignOut -> performSignOut()
        }
    }

    private suspend fun performSignOut() {
        signOut()
        dispatchAction(CredentialListAction.SignOutSucceeded)
    }

    private fun observeEntries() {
        getCredentials()
            .onEach { dispatchAction(CredentialListAction.EntriesLoaded(it)) }
            .catch { dispatchAction(CredentialListAction.LoadFailed(AppError(it.message ?: "Unknown error", it))) }
            .launchIn(viewModelScope)
    }
}
