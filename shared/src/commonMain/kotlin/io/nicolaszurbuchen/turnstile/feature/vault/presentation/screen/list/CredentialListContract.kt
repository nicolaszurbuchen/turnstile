package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.ui.AppError

sealed interface CredentialListIntent {
    data class EntryClicked(val id: String) : CredentialListIntent
    data object CreateClicked : CredentialListIntent
    data object SignOutClicked : CredentialListIntent
    data object DismissStreamError : CredentialListIntent
    data object RetryInitialLoad : CredentialListIntent
}

sealed interface CredentialListLabel {
    data class NavigateToDetail(val id: String) : CredentialListLabel
    data object NavigateToCreate : CredentialListLabel
    data object NavigateToAuth : CredentialListLabel
}

sealed interface CredentialListAction {
    data object ObserveEntries : CredentialListAction
}

sealed interface CredentialListMessage {
    data class EntriesLoaded(val entries: List<Credential>) : CredentialListMessage
    data class InitialLoadFailed(val error: AppError) : CredentialListMessage
    data class StreamErrored(val error: AppError) : CredentialListMessage
    data object DismissStreamError : CredentialListMessage
    data object ResetToLoading : CredentialListMessage
}

data class CredentialListState(
    val entries: List<CredentialUi> = emptyList(),
    val initialLoad: InitialLoad = InitialLoad.Loading,
    val streamError: AppError? = null,
) {
    val isEmpty: Boolean get() = entries.isEmpty()
}

sealed interface InitialLoad {
    data object Loading : InitialLoad
    data object Loaded : InitialLoad
    data class Failed(val error: AppError) : InitialLoad
}

data class CredentialUi(
    val id: String,
    val title: String,
    val username: String,
)

fun Credential.toUiModel() = CredentialUi(
    id = id,
    title = title,
    username = username,
)

fun List<Credential>.toUiModels() = map { it.toUiModel() }
