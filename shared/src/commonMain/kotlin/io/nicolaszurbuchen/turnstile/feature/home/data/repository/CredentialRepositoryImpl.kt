package io.nicolaszurbuchen.turnstile.feature.home.data.repository

import io.nicolaszurbuchen.turnstile.feature.home.data.datasource.remote.CredentialRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.CredentialRepository

class CredentialRepositoryImpl(
    private val remoteDataSource: CredentialRemoteDataSource
) : CredentialRepository {
    override suspend fun createCredential(userId: String, credential: Credential) {
        remoteDataSource.createCredential(userId, credential)
    }

    override suspend fun getCredential(userId: String, credentialId: String): Credential? {
        return remoteDataSource.getCredential(userId, credentialId)
    }

    override suspend fun getCredentials(userId: String): List<Credential> {
        return remoteDataSource.getCredentials(userId)
    }

    override suspend fun updateCredential(userId: String, credential: Credential) {
        remoteDataSource.updateCredential(userId, credential)
    }

    override suspend fun deleteCredential(userId: String, credentialId: String) {
        remoteDataSource.deleteCredential(userId, credentialId)
    }
}
