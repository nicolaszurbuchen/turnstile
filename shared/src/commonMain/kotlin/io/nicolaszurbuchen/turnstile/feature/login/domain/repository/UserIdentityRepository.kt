package io.nicolaszurbuchen.turnstile.feature.login.domain.repository

interface UserIdentityRepository {
    suspend fun signInWithEmail(
        email: String,
        password: String,
    )

    suspend fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
    )

    suspend fun sendPasswordResetEmail(email: String)
}
