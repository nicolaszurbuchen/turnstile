package io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote

import io.nicolaszurbuchen.turnstile.feature.auth.domain.model.User

interface UserRemoteDataSource {
    suspend fun createUser(user: User)

    suspend fun getUser(uid: String): User?
}
