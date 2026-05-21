package io.nicolaszurbuchen.turnstile.feature.home.domain.repository

import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential

interface CredentialRepository {
    suspend fun createCredential(credential: Credential)

    suspend fun getCredential(credentialId: String): Credential?

    suspend fun getCredentials(): List<Credential>

    suspend fun updateCredential(credential: Credential)

    suspend fun deleteCredential(credentialId: String)
}
