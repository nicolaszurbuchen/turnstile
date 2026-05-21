package io.nicolaszurbuchen.turnstile.feature.home.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.CredentialRepository

class DeleteCredentialUseCase(
    private val userIdentityRepository: UserIdentityRepository,
    private val credentialRepository: CredentialRepository
) {
    suspend operator fun invoke(credentialId: String) {
        val userId = userIdentityRepository.getCurrentUserId()
            ?: throw Exception("User not authenticated")
        credentialRepository.deleteCredential(userId, credentialId)
    }
}
