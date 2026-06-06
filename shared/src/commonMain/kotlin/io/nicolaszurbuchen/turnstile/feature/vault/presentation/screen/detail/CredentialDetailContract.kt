package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.ui.AppError

sealed interface CredentialDetailIntent {
    data object EditClicked : CredentialDetailIntent
    data object DeleteClicked : CredentialDetailIntent
    data object BackClicked : CredentialDetailIntent
    data object Retry : CredentialDetailIntent
}

sealed interface CredentialDetailLabel {
    data class NavigateToEdit(val id: String) : CredentialDetailLabel
    data object NavigateBack : CredentialDetailLabel
}

sealed interface CredentialDetailAction {
    data class LoadCredential(val id: String) : CredentialDetailAction
}

sealed interface CredentialDetailMessage {
    data class CredentialLoaded(val credential: Credential) : CredentialDetailMessage
    data class LoadFailed(val error: AppError) : CredentialDetailMessage
    data object Deleted : CredentialDetailMessage
    data class DeleteFailed(val error: AppError) : CredentialDetailMessage
    data object Loading : CredentialDetailMessage
}

data class CredentialDetailState(
    val credential: CredentialUi? = null,
    val initialLoad: InitialLoad = InitialLoad.Loading,
    val deleteError: AppError? = null,
)

sealed interface InitialLoad {
    data object Loading : InitialLoad
    data object Loaded : InitialLoad
    data class Failed(val error: AppError) : InitialLoad
}

data class CredentialUi(
    val id: String,
    val title: String,
    val username: String,
    val password: String,
    val memo: String?,
)

fun Credential.toUiModel() = CredentialUi(
    id = id,
    title = title,
    username = username,
    password = password,
    memo = memo,
)
