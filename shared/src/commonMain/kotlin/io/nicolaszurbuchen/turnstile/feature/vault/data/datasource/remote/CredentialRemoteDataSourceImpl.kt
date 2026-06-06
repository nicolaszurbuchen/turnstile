package io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote

import dev.gitlive.firebase.firestore.FirebaseFirestore
import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.dto.CredentialDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CredentialRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore,
) : CredentialRemoteDataSource {
    private fun collection(userId: String) = firestore.collection("users").document(userId).collection("credentials")

    override fun getCredentials(userId: String): Flow<List<CredentialDto>> =
        collection(userId).snapshots.map { query ->
            query.documents.map { it.data() }
        }

    override suspend fun getCredential(
        userId: String,
        credentialId: String,
    ): CredentialDto? {
        val doc = collection(userId).document(credentialId).get()
        return if (doc.exists) doc.data() else null
    }

    override suspend fun saveCredential(
        userId: String,
        credentialDto: CredentialDto,
    ) {
        val docRef =
            if (credentialDto.id.isEmpty()) {
                collection(userId).document(kotlin.random.Random.nextInt().toString())
            } else {
                collection(userId).document(credentialDto.id)
            }
        val finalCredential =
            if (credentialDto.id.isEmpty()) {
                credentialDto.copy(id = docRef.id)
            } else {
                credentialDto
            }
        docRef.set(finalCredential)
    }

    override suspend fun deleteCredential(
        userId: String,
        credentialId: String,
    ) {
        collection(userId).document(credentialId).delete()
    }
}
