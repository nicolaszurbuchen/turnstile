package io.nicolaszurbuchen.turnstile.feature.home.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository
import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.CredentialRepository

class GetCredentialsUseCase(
    private val userIdentityRepository: UserIdentityRepository,
    private val credentialRepository: CredentialRepository
) {
    suspend operator fun invoke(): List<Credential> {
        val userId = userIdentityRepository.getCurrentUserId()
            ?: throw Exception("User not authenticated")
        return credentialRepository.getCredentials(userId)
    }
}
