package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.mvi.Next
import io.nicolaszurbuchen.turnstile.infra.mvi.Reducer

object CredentialEditorReducer : Reducer<CredentialEditorStateImpl, CredentialEditorTrigger, CredentialEditorCommand, CredentialEditorEvent> {
    override fun reduce(
        state: CredentialEditorStateImpl,
        trigger: CredentialEditorTrigger,
    ): Next<CredentialEditorStateImpl, CredentialEditorCommand, CredentialEditorEvent> =
        when (trigger) {
            is CredentialEditorAction.CredentialLoaded -> {
                Next(
                    state = state.copy(
                        id = trigger.credential.id,
                        title = trigger.credential.title,
                        username = trigger.credential.username,
                        password = trigger.credential.password,
                        memo = trigger.credential.memo
                    )
                )
            }

            is CredentialEditorIntent.TitleChanged -> {
                Next(state = state.copy(title = trigger.value))
            }

            is CredentialEditorIntent.UsernameChanged -> {
                Next(state = state.copy(username = trigger.value))
            }

            is CredentialEditorIntent.PasswordChanged -> {
                Next(state = state.copy(password = trigger.value))
            }

            is CredentialEditorIntent.MemoChanged -> {
                Next(state = state.copy(memo = trigger.value))
            }

            CredentialEditorIntent.SaveClicked -> {
                val credential = Credential(
                    id = state.id,
                    title = state.title,
                    username = state.username,
                    password = state.password,
                    memo = state.memo
                )
                Next(state = state, commands = listOf(CredentialEditorCommand.SaveCredential(credential)))
            }

            CredentialEditorAction.Saving -> {
                Next(state = state.copy(isSaving = true))
            }

            CredentialEditorAction.Saved -> {
                Next(state = state, events = listOf(CredentialEditorEvent.NavigateBack))
            }

            is CredentialEditorAction.SaveFailed -> {
                Next(state = state.copy(isSaving = false), events = listOf(CredentialEditorEvent.ShowError(trigger.error)))
            }

            CredentialEditorIntent.CancelClicked -> {
                Next(state = state, events = listOf(CredentialEditorEvent.NavigateBack))
            }
        }
}
