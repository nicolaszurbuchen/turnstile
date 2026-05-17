package io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard

import androidx.lifecycle.viewModelScope
import io.nicolaszurbuchen.turnstile.core.mvi.MviViewModel
import io.nicolaszurbuchen.turnstile.core.ui.AppError
import io.nicolaszurbuchen.turnstile.core.ui.Loadable
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.PasswordRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: PasswordRepository,
) : MviViewModel<Loadable<DashboardState>, DashboardTrigger, DashboardIntent, DashboardAction, DashboardCommand, DashboardEvent>(
        initialState = Loadable.Loading,
        reducer = DashboardReducer,
    ) {
    init {
        viewModelScope.launch { executeCommand(DashboardCommand.LoadEntries) }
    }

    override suspend fun executeCommand(command: DashboardCommand) {
        when (command) {
            DashboardCommand.LoadEntries -> loadEntries()
            DashboardCommand.Refresh -> refresh()
            is DashboardCommand.DeleteEntry -> deleteEntry(command.id)
        }
    }

    private suspend fun loadEntries() {
        runCatching { repository.getEntries() }
            .onSuccess { dispatchAction(DashboardAction.EntriesLoaded(it)) }
            .onFailure { dispatchAction(DashboardAction.LoadFailed(it.toAppError())) }
    }

    private suspend fun refresh() {
        runCatching { repository.getEntries() }
            .onSuccess { dispatchAction(DashboardAction.RefreshSucceeded(it)) }
            .onFailure { dispatchAction(DashboardAction.RefreshFailed(it.toAppError())) }
    }

    private suspend fun deleteEntry(id: String) {
        runCatching { repository.deleteEntry(id) }
            .onSuccess { dispatchAction(DashboardAction.EntryDeleted(id)) }
            .onFailure { dispatchAction(DashboardAction.DeleteFailed(it.toAppError())) }
    }

    private fun Throwable.toAppError() = AppError(message ?: "Unknown error", this)
}
