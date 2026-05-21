package io.nicolaszurbuchen.turnstile.feature.home.domain.repository

import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential

interface CredentialRepository {
    suspend fun createCredential(userId: String, credential: Credential)
    suspend fun getCredential(userId: String, credentialId: String): Credential?
    suspend fun getCredentials(userId: String): List<Credential>
    suspend fun updateCredential(userId: String, credential: Credential)
    suspend fun deleteCredential(userId: String, credentialId: String)
}
