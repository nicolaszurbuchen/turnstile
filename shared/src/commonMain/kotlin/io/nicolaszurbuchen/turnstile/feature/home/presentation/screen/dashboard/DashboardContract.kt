package io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard

import io.nicolaszurbuchen.turnstile.core.mvi.Action
import io.nicolaszurbuchen.turnstile.core.mvi.Command
import io.nicolaszurbuchen.turnstile.core.mvi.Event
import io.nicolaszurbuchen.turnstile.core.mvi.Intent
import io.nicolaszurbuchen.turnstile.core.mvi.State
import io.nicolaszurbuchen.turnstile.core.mvi.Trigger
import io.nicolaszurbuchen.turnstile.core.ui.AppError
import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential

// ─── UI model ─────────────────────────────────────────────────────────────────

data class CredentialUiModel(
    val id: String,
    val name: String,
    val type: String,
)

fun Credential.toUiModel() = CredentialUiModel(id = id, name = name, type = type)

fun List<Credential>.toDashboardState() = DashboardState(entries = map { it.toUiModel() })

data class DashboardState(
    val entries: List<CredentialUiModel> = emptyList(),
    val refreshing: Boolean = false,
    val refreshError: AppError? = null,
) : State {
    val isEmpty: Boolean get() = entries.isEmpty()
}

sealed interface DashboardTrigger : Trigger

sealed interface DashboardIntent :
    DashboardTrigger,
    Intent {
    data object Retry : DashboardIntent

    data object Refresh : DashboardIntent

    data object DismissRefreshError : DashboardIntent

    data class EntryClicked(
        val id: String,
    ) : DashboardIntent

    data class DeleteEntry(
        val id: String,
    ) : DashboardIntent
}

sealed interface DashboardAction :
    DashboardTrigger,
    Action {
    data class EntriesLoaded(
        val entries: List<Credential>,
    ) : DashboardAction

    data class LoadFailed(
        val error: AppError,
    ) : DashboardAction

    data class RefreshSucceeded(
        val entries: List<Credential>,
    ) : DashboardAction

    data class RefreshFailed(
        val error: AppError,
    ) : DashboardAction

    data class EntryDeleted(
        val id: String,
    ) : DashboardAction

    data class DeleteFailed(
        val error: AppError,
    ) : DashboardAction
}

sealed interface DashboardCommand : Command {
    data object LoadEntries : DashboardCommand

    data object Refresh : DashboardCommand

    data class DeleteEntry(
        val id: String,
    ) : DashboardCommand
}

sealed interface DashboardEvent : Event {
    data class NavigateToDetail(
        val id: String,
    ) : DashboardEvent

    data class ShowDeleteError(
        val error: AppError,
    ) : DashboardEvent
}
