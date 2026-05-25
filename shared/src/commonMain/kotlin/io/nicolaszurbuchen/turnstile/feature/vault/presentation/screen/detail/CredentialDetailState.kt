package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential

data class CredentialDetailStateImpl(
    override val credential: Credential? = null,
) : CredentialDetailState
