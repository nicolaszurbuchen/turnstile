package io.nicolaszurbuchen.turnstile.feature.home.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.CredentialRepository

class DeleteCredentialUseCase(
    private val credentialRepository: CredentialRepository,
) {
    suspend operator fun invoke(credentialId: String) {
        credentialRepository.deleteCredential(credentialId)
    }
}
