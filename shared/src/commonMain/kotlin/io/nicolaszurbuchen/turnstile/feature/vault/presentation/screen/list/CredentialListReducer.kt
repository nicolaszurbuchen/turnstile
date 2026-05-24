package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import io.nicolaszurbuchen.turnstile.infra.mvi.Next
import io.nicolaszurbuchen.turnstile.infra.mvi.Reducer
import io.nicolaszurbuchen.turnstile.infra.ui.Loadable

object CredentialListReducer : Reducer<Loadable<CredentialListState>, CredentialListTrigger, CredentialListCommand, CredentialListEvent> {
    override fun reduce(
        state: Loadable<CredentialListState>,
        trigger: CredentialListTrigger,
    ): Next<Loadable<CredentialListState>, CredentialListCommand, CredentialListEvent> =
        when (trigger) {
            is CredentialListAction.EntriesLoaded -> {
                Next(state = Loadable.Success(trigger.entries.toCredentialListState()))
            }

            is CredentialListAction.LoadFailed -> {
                Next(state = Loadable.Failure(trigger.error))
            }

            is CredentialListIntent.EntryClicked -> {
                Next(state = state, events = listOf(CredentialListEvent.NavigateToDetail(trigger.id)))
            }

            CredentialListIntent.CreateClicked -> {
                Next(state = state, events = listOf(CredentialListEvent.NavigateToCreate))
            }
        }
}
