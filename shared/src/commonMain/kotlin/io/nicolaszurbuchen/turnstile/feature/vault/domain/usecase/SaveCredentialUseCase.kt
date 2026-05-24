package io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.vault.domain.repository.CredentialRepository

class SaveCredentialUseCase(
    private val credentialRepository: CredentialRepository,
) {
    suspend operator fun invoke(credential: Credential) {
        credentialRepository.saveCredential(credential)
    }
}
