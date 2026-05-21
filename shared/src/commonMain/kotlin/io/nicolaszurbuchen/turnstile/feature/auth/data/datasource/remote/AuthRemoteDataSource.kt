package io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote

import dev.gitlive.firebase.auth.FirebaseUser

interface AuthRemoteDataSource {
    suspend fun signInWithEmail(
        email: String,
        password: String,
    ): FirebaseUser

    suspend fun signUpWithEmail(
        email: String,
        password: String,
    ): FirebaseUser

    suspend fun signOut()

    suspend fun deleteAccount()
}
