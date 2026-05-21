package io.nicolaszurbuchen.turnstile.feature.home.data.repository

import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import io.nicolaszurbuchen.turnstile.feature.home.data.datasource.remote.CredentialRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.CredentialRepository

class CredentialRepositoryImpl(
    private val remoteDataSource: CredentialRemoteDataSource,
    private val sessionRepository: SessionRepository,
) : CredentialRepository {
    private val userId: String
        get() = sessionRepository.currentUserId() ?: throw Exception("User not authenticated")

    override suspend fun createCredential(credential: Credential) {
        remoteDataSource.createCredential(userId, credential)
    }

    override suspend fun getCredential(credentialId: String): Credential? = remoteDataSource.getCredential(userId, credentialId)

    override suspend fun getCredentials(): List<Credential> = remoteDataSource.getCredentials(userId)

    override suspend fun updateCredential(credential: Credential) {
        remoteDataSource.updateCredential(userId, credential)
    }

    override suspend fun deleteCredential(credentialId: String) {
        remoteDataSource.deleteCredential(userId, credentialId)
    }
}
