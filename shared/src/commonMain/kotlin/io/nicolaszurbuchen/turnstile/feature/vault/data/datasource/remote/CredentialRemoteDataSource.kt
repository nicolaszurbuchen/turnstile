package io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRemoteDataSource {
    fun getCredentials(userId: String): Flow<List<Credential>>
    suspend fun getCredential(userId: String, credentialId: String): Credential?
    suspend fun saveCredential(userId: String, credential: Credential)
    suspend fun deleteCredential(userId: String, credentialId: String)
}
