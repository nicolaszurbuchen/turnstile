package io.nicolaszurbuchen.turnstile.feature.vault.domain.repository

import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {
    fun getCredentials(): Flow<List<Credential>>

    suspend fun getCredential(id: String): Credential?

    suspend fun saveCredential(credential: Credential)

    suspend fun deleteCredential(id: String)
}
