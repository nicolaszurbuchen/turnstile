package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.mvi.Action
import io.nicolaszurbuchen.turnstile.infra.mvi.Command
import io.nicolaszurbuchen.turnstile.infra.mvi.Event
import io.nicolaszurbuchen.turnstile.infra.mvi.Intent
import io.nicolaszurbuchen.turnstile.infra.mvi.State
import io.nicolaszurbuchen.turnstile.infra.mvi.Trigger
import io.nicolaszurbuchen.turnstile.infra.ui.AppError

sealed interface CredentialDetailState : State {
    val credential: Credential?
}

sealed interface CredentialDetailTrigger : Trigger

sealed interface CredentialDetailIntent :
    CredentialDetailTrigger,
    Intent {
    data object EditClicked : CredentialDetailIntent

    data object DeleteClicked : CredentialDetailIntent

    data object BackClicked : CredentialDetailIntent
}

sealed interface CredentialDetailAction :
    CredentialDetailTrigger,
    Action {
    data class CredentialLoaded(
        val credential: Credential,
    ) : CredentialDetailAction

    data class LoadFailed(
        val error: AppError,
    ) : CredentialDetailAction

    data object Deleted : CredentialDetailAction

    data class DeleteFailed(
        val error: AppError,
    ) : CredentialDetailAction
}

sealed interface CredentialDetailCommand : Command {
    data class LoadCredential(
        val id: String,
    ) : CredentialDetailCommand

    data class DeleteCredential(
        val id: String,
    ) : CredentialDetailCommand
}

sealed interface CredentialDetailEvent : Event {
    data class NavigateToEdit(
        val id: String,
    ) : CredentialDetailEvent

    data object NavigateBack : CredentialDetailEvent
}
