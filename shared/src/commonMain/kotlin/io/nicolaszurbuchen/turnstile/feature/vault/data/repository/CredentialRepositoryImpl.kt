package io.nicolaszurbuchen.turnstile.feature.vault.data.repository

import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.CredentialRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.mapper.CredentialMapper
import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import io.nicolaszurbuchen.turnstile.feature.vault.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class CredentialRepositoryImpl(
    private val remoteDataSource: CredentialRemoteDataSource,
    private val sessionRepository: SessionRepository,
    private val mapper: CredentialMapper,
) : CredentialRepository {
    private val userId: String?
        get() = sessionRepository.getCurrentUserId()

    override fun getCredentials(): Flow<List<Credential>> =
        userId?.let { id ->
            remoteDataSource.getCredentials(id).map { list ->
                list.map { mapper.toDomain(it) }
            }
        } ?: emptyFlow()

    override suspend fun getCredential(id: String): Credential? =
        userId?.let { userId ->
            remoteDataSource.getCredential(userId, id)?.let { mapper.toDomain(it) }
        }

    override suspend fun saveCredential(credential: Credential) {
        userId?.let { userId ->
            remoteDataSource.saveCredential(userId, mapper.toDto(credential))
        }
    }

    override suspend fun deleteCredential(id: String) {
        userId?.let { userId ->
            remoteDataSource.deleteCredential(userId, id)
        }
    }
}
