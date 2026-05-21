package io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote

import dev.gitlive.firebase.firestore.FirebaseFirestore
import io.nicolaszurbuchen.turnstile.feature.auth.domain.model.User

class UserRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore,
) : UserRemoteDataSource {
    override suspend fun createUser(user: User) {
        firestore.collection("users").document(user.id).set(user)
    }

    override suspend fun getUser(uid: String): User? {
        val userDoc = firestore.collection("users").document(uid).get()
        return if (userDoc.exists) {
            userDoc.data<User>()
        } else {
            null
        }
    }
}
