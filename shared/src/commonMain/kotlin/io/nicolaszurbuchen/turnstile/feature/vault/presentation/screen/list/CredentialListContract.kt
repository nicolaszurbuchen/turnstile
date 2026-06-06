package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.mvi.Action
import io.nicolaszurbuchen.turnstile.infra.mvi.Command
import io.nicolaszurbuchen.turnstile.infra.mvi.Event
import io.nicolaszurbuchen.turnstile.infra.mvi.Intent
import io.nicolaszurbuchen.turnstile.infra.mvi.State
import io.nicolaszurbuchen.turnstile.infra.mvi.Trigger
import io.nicolaszurbuchen.turnstile.infra.ui.AppError

// ─── Contract ─────────────────────────────────────────────────────────────────

data class CredentialListState(
    val entries: List<CredentialUiModel> = emptyList(),
) : State {
    val isEmpty: Boolean get() = entries.isEmpty()
}

sealed interface CredentialListTrigger : Trigger

sealed interface CredentialListIntent :
    CredentialListTrigger,
    Intent {
    data class EntryClicked(
        val id: String,
    ) : CredentialListIntent

    data object CreateClicked : CredentialListIntent

    data object SignOutClicked : CredentialListIntent
}

sealed interface CredentialListAction :
    CredentialListTrigger,
    Action {
    data class EntriesLoaded(
        val entries: List<Credential>,
    ) : CredentialListAction

    data class LoadFailed(
        val error: AppError,
    ) : CredentialListAction

    data object SignOutSucceeded : CredentialListAction
}

sealed interface CredentialListCommand : Command {
    data object ObserveEntries : CredentialListCommand

    data object SignOut : CredentialListCommand
}

sealed interface CredentialListEvent : Event {
    data class NavigateToDetail(
        val id: String,
    ) : CredentialListEvent

    data object NavigateToCreate : CredentialListEvent

    data object NavigateToAuth : CredentialListEvent
}
