package io.nicolaszurbuchen.turnstile.feature.auth.domain.repository

import io.nicolaszurbuchen.turnstile.feature.auth.domain.model.User

interface UserIdentityRepository {
    suspend fun signInWithEmail(
        email: String,
        password: String,
    ): User

    suspend fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
    ): User

    suspend fun signInWithGoogle(): User

    suspend fun signInWithApple(): User

    suspend fun signOut()
}
