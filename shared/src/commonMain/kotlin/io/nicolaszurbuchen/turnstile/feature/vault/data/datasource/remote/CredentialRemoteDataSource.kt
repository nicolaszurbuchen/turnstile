package io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote

import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.dto.CredentialDto
import kotlinx.coroutines.flow.Flow

interface CredentialRemoteDataSource {
    fun getCredentials(userId: String): Flow<List<CredentialDto>>

    suspend fun getCredential(
        userId: String,
        credentialId: String,
    ): CredentialDto?

    suspend fun saveCredential(
        userId: String,
        credentialDto: CredentialDto,
    )

    suspend fun deleteCredential(
        userId: String,
        credentialId: String,
    )
}
