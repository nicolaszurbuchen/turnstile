package io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote

import dev.gitlive.firebase.firestore.FirebaseFirestore
import io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote.dto.UserDto

class UserRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore,
) : UserRemoteDataSource {
    override suspend fun createUser(user: UserDto) {
        firestore.collection("users").document(user.id).set(user)
    }

    override suspend fun getUser(uid: String): UserDto? {
        val userDoc = firestore.collection("users").document(uid).get()
        return if (userDoc.exists) {
            userDoc.data<UserDto>()
        } else {
            null
        }
    }
}
