package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import io.nicolaszurbuchen.turnstile.infra.ui.InitialLoad

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

data class CredentialUi(
    val id: String,
    val title: String,
    val username: String,
    val password: String,
)

fun Credential.toUiModel() = CredentialUi(
    id = id,
    title = title,
    username = username,
    password = password,
)

fun List<Credential>.toUiModels() = map { it.toUiModel() }
