package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import io.nicolaszurbuchen.turnstile.infra.mvi.Next
import io.nicolaszurbuchen.turnstile.infra.mvi.Reducer
import io.nicolaszurbuchen.turnstile.infra.ui.Loadable
import io.nicolaszurbuchen.turnstile.infra.ui.onSuccess

object CredentialDetailReducer :
    Reducer<
        Loadable<CredentialDetailStateImpl>,
        CredentialDetailTrigger,
        CredentialDetailCommand,
        CredentialDetailEvent,
    > {
    override fun reduce(
        state: Loadable<CredentialDetailStateImpl>,
        trigger: CredentialDetailTrigger,
    ): Next<Loadable<CredentialDetailStateImpl>, CredentialDetailCommand, CredentialDetailEvent> =
        when (trigger) {
            is CredentialDetailAction.CredentialLoaded -> {
                Next(state = Loadable.Success(CredentialDetailStateImpl(trigger.credential)))
            }

            is CredentialDetailAction.LoadFailed -> {
                Next(state = Loadable.Failure(trigger.error))
            }

            CredentialDetailIntent.EditClicked -> {
                state.onSuccess { content ->
                    Next(
                        state = state,
                        events =
                            content.credential?.id?.let {
                                listOf(
                                    CredentialDetailEvent.NavigateToEdit(
                                        it,
                                    ),
                                )
                            } ?: emptyList(),
                    )
                }
            }

            CredentialDetailIntent.DeleteClicked -> {
                state.onSuccess { content ->
                    Next(
                        state = state,
                        commands =
                            content.credential?.id?.let {
                                listOf(
                                    CredentialDetailCommand.DeleteCredential(
                                        it,
                                    ),
                                )
                            } ?: emptyList(),
                    )
                }
            }

            CredentialDetailAction.Deleted -> {
                Next(state = state, events = listOf(CredentialDetailEvent.NavigateBack))
            }

            CredentialDetailIntent.BackClicked -> {
                Next(state = state, events = listOf(CredentialDetailEvent.NavigateBack))
            }

            else -> {
                Next(state = state)
            }
        }
}
