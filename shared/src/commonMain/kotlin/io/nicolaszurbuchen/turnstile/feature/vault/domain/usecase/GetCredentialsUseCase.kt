package io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.vault.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class GetCredentialsUseCase(
    private val credentialRepository: CredentialRepository,
) {
    operator fun invoke(): Flow<List<Credential>> = credentialRepository.getCredentials()
}
