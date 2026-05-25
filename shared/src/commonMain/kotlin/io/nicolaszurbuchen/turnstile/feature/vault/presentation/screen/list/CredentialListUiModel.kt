package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential

data class CredentialUiModel(
    val id: String,
    val title: String,
    val username: String,
)

fun Credential.toUiModel() = CredentialUiModel(id = id, title = title, username = username)

fun List<Credential>.toCredentialListState() = CredentialListStateImpl(entries = map { it.toUiModel() })
