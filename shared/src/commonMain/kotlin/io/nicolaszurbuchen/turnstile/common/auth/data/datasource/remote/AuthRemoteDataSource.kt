package io.nicolaszurbuchen.turnstile.common.auth.data.datasource.remote

interface AuthRemoteDataSource {
    suspend fun signInWithEmail(
        email: String,
        password: String,
    )

    suspend fun signUpWithEmail(
        email: String,
        password: String,
    ): String

    suspend fun sendPasswordResetEmail(email: String)

    suspend fun signOut()

    suspend fun deleteAccount()
}
