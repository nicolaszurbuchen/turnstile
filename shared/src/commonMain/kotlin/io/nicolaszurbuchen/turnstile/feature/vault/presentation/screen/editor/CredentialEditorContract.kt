package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import io.nicolaszurbuchen.turnstile.infra.ui.InitialLoad

sealed interface CredentialEditorIntent {
    data class TitleChanged(val value: String) : CredentialEditorIntent
    data class UsernameChanged(val value: String) : CredentialEditorIntent
    data class PasswordChanged(val value: String) : CredentialEditorIntent
    data class MemoChanged(val value: String) : CredentialEditorIntent
    data object SaveClicked : CredentialEditorIntent
    data object CancelClicked : CredentialEditorIntent
    data object Retry : CredentialEditorIntent
    data object DismissSaveError : CredentialEditorIntent
}

sealed interface CredentialEditorLabel {
    data object NavigateBack : CredentialEditorLabel
    data class ShowError(val error: AppError) : CredentialEditorLabel
}

sealed interface CredentialEditorAction {
    data class LoadCredential(val id: String) : CredentialEditorAction
}

sealed interface CredentialEditorMessage {
    data class CredentialLoaded(val credential: Credential) : CredentialEditorMessage
    data class InitialLoadFailed(val error: AppError) : CredentialEditorMessage
    data object Saving : CredentialEditorMessage
    data object Saved : CredentialEditorMessage
    data class SaveFailed(val error: AppError) : CredentialEditorMessage
    data object ResetToLoading : CredentialEditorMessage
    data object DismissSaveError : CredentialEditorMessage
}

data class CredentialEditorState(
    val id: String = "",
    val title: String = "",
    val username: String = "",
    val password: String = "",
    val memo: String? = "",
    val isSaving: Boolean = false,
    val initialLoad: InitialLoad = InitialLoad.Loaded,
    val saveError: AppError? = null,
)

fun CredentialEditorState.toDomain() = Credential(
    id = id,
    title = title,
    username = username,
    password = password,
    memo = memo,
)
