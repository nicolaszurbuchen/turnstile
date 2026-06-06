package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import io.nicolaszurbuchen.turnstile.infra.mvi.Next
import io.nicolaszurbuchen.turnstile.infra.mvi.Reducer
import io.nicolaszurbuchen.turnstile.infra.ui.Loadable

object CredentialListReducer :
    Reducer<
        Loadable<CredentialListStateImpl>,
        CredentialListTrigger,
        CredentialListCommand,
        CredentialListEvent,
    > {
    override fun reduce(
        state: Loadable<CredentialListStateImpl>,
        trigger: CredentialListTrigger,
    ): Next<Loadable<CredentialListStateImpl>, CredentialListCommand, CredentialListEvent> =
        when (trigger) {
            is CredentialListAction.EntriesLoaded -> {
                Next(state = Loadable.Success(trigger.entries.toCredentialListState()))
            }

            is CredentialListAction.LoadFailed -> {
                Next(state = Loadable.Failure(trigger.error))
            }

            is CredentialListIntent.EntryClicked -> {
                Next(
                    state = state,
                    events = listOf(CredentialListEvent.NavigateToDetail(trigger.id)),
                )
            }

            CredentialListIntent.CreateClicked -> {
                Next(state = state, events = listOf(CredentialListEvent.NavigateToCreate))
            }

            CredentialListIntent.SignOutClicked -> {
                Next(state = state, commands = listOf(CredentialListCommand.SignOut))
            }

            CredentialListAction.SignOutSucceeded -> {
                Next(state = state, events = listOf(CredentialListEvent.NavigateToAuth))
            }
        }
}
