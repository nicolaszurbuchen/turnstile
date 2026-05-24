package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.mvi.Action
import io.nicolaszurbuchen.turnstile.infra.mvi.Command
import io.nicolaszurbuchen.turnstile.infra.mvi.Event
import io.nicolaszurbuchen.turnstile.infra.mvi.Intent
import io.nicolaszurbuchen.turnstile.infra.mvi.State
import io.nicolaszurbuchen.turnstile.infra.mvi.Trigger
import io.nicolaszurbuchen.turnstile.infra.ui.AppError

data class CredentialEditorState(
    val id: String = "",
    val title: String = "",
    val username: String = "",
    val password: String = "",
    val memo: String = "",
    val isSaving: Boolean = false,
) : State

sealed interface CredentialEditorTrigger : Trigger

sealed interface CredentialEditorIntent :
    CredentialEditorTrigger,
    Intent {
    data class TitleChanged(val value: String) : CredentialEditorIntent
    data class UsernameChanged(val value: String) : CredentialEditorIntent
    data class PasswordChanged(val value: String) : CredentialEditorIntent
    data class MemoChanged(val value: String) : CredentialEditorIntent
    data object SaveClicked : CredentialEditorIntent
    data object CancelClicked : CredentialEditorIntent
}

sealed interface CredentialEditorAction :
    CredentialEditorTrigger,
    Action {
    data class CredentialLoaded(val credential: Credential) : CredentialEditorAction
    data object Saving : CredentialEditorAction
    data object Saved : CredentialEditorAction
    data class SaveFailed(val error: AppError) : CredentialEditorAction
}

sealed interface CredentialEditorCommand : Command {
    data class LoadCredential(val id: String) : CredentialEditorCommand
    data class SaveCredential(val credential: Credential) : CredentialEditorCommand
}

sealed interface CredentialEditorEvent : Event {
    data object NavigateBack : CredentialEditorEvent
    data class ShowError(val error: AppError) : CredentialEditorEvent
}
