package io.nicolaszurbuchen.turnstile.feature.home.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.CredentialRepository

class GetCredentialsUseCase(
    private val credentialRepository: CredentialRepository,
) {
    suspend operator fun invoke(): List<Credential> = credentialRepository.getCredentials()
}
