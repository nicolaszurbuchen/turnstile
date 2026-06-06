package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

data class CredentialEditorStateImpl(
    override val id: String = "",
    override val title: String = "",
    override val username: String = "",
    override val password: String = "",
    override val memo: String? = "",
    override val isSaving: Boolean = false,
) : CredentialEditorState
