package io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote

import dev.gitlive.firebase.firestore.FirebaseFirestore
import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CredentialRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore,
) : CredentialRemoteDataSource {

    private fun collection(userId: String) =
        firestore.collection("users").document(userId).collection("credentials")

    override fun getCredentials(userId: String): Flow<List<Credential>> =
        collection(userId).snapshots.map { query ->
            query.documents.map { it.data() }
        }

    override suspend fun getCredential(userId: String, credentialId: String): Credential? {
        val doc = collection(userId).document(credentialId).get()
        return if (doc.exists) doc.data() else null
    }

    override suspend fun saveCredential(userId: String, credential: Credential) {
        val docRef = if (credential.id.isEmpty()) {
            collection(userId).document(kotlin.random.Random.nextInt().toString())
        } else {
            collection(userId).document(credential.id)
        }
        val finalCredential = if (credential.id.isEmpty()) {
            credential.copy(id = docRef.id)
        } else {
            credential
        }
        docRef.set(finalCredential)
    }

    override suspend fun deleteCredential(userId: String, credentialId: String) {
        collection(userId).document(credentialId).delete()
    }
}
