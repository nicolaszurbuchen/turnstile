package io.nicolaszurbuchen.turnstile.feature.vault.data.repository

import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.CredentialRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.vault.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class CredentialRepositoryImpl(
    private val remoteDataSource: CredentialRemoteDataSource,
    private val sessionRepository: SessionRepository,
) : CredentialRepository {

    private val userId: String?
        get() = sessionRepository.getCurrentUserId()

    override fun getCredentials(): Flow<List<Credential>> {
        return userId?.let { remoteDataSource.getCredentials(it) } ?: emptyFlow()
    }

    override suspend fun getCredential(id: String): Credential? {
        return userId?.let { remoteDataSource.getCredential(it, id) }
    }

    override suspend fun saveCredential(credential: Credential) {
        userId?.let { remoteDataSource.saveCredential(it, credential) }
    }

    override suspend fun deleteCredential(id: String) {
        userId?.let { remoteDataSource.deleteCredential(it, id) }
    }
}
