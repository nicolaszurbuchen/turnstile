package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

data class CredentialListStateImpl(
    override val entries: List<CredentialUiModel> = emptyList(),
) : CredentialListState {
    override val isEmpty: Boolean get() = entries.isEmpty()
}
