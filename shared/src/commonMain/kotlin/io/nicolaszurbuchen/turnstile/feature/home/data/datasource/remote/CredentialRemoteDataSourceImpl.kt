package io.nicolaszurbuchen.turnstile.feature.home.data.datasource.remote

import dev.gitlive.firebase.firestore.FirebaseFirestore
import io.nicolaszurbuchen.turnstile.feature.home.domain.model.Credential

class CredentialRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore,
) : CredentialRemoteDataSource {
    override suspend fun createCredential(
        userId: String,
        credential: Credential,
    ) {
        firestore.collection("users").document(userId)
            .collection("credentials").document(credential.id).set(credential)
    }

    override suspend fun getCredential(
        userId: String,
        credentialId: String,
    ): Credential? {
        val doc =
            firestore.collection("users").document(userId)
                .collection("credentials").document(credentialId).get()
        return if (doc.exists) doc.data<Credential>() else null
    }

    override suspend fun getCredentials(userId: String): List<Credential> {
        val query =
            firestore.collection("users").document(userId)
                .collection("credentials").get()
        return query.documents.map { it.data<Credential>() }
    }

    override suspend fun updateCredential(
        userId: String,
        credential: Credential,
    ) {
        firestore.collection("users").document(userId)
            .collection("credentials").document(credential.id).set(credential)
    }

    override suspend fun deleteCredential(
        userId: String,
        credentialId: String,
    ) {
        firestore.collection("users").document(userId)
            .collection("credentials").document(credentialId).delete()
    }
}
