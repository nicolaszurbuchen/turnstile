package io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser

class AuthRemoteDataSourceImpl(
    private val auth: FirebaseAuth,
) : AuthRemoteDataSource {
    override suspend fun signInWithEmail(
        email: String,
        password: String,
    ): FirebaseUser {
        val result = auth.signInWithEmailAndPassword(email, password)
        return result.user ?: throw Exception("Sign in failed: User is null")
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
    ): FirebaseUser {
        val result = auth.createUserWithEmailAndPassword(email, password)
        return result.user ?: throw Exception("Sign up failed: User is null")
    }

    override suspend fun signInWithGoogle(): FirebaseUser =
        throw UnsupportedOperationException("Google Sign-In not yet implemented in commonMain")

    override suspend fun signInWithApple(): FirebaseUser =
        throw UnsupportedOperationException("Apple Sign-In not yet implemented in commonMain")

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteAccount() {
        auth.currentUser?.delete()
    }

    override fun getCurrentUserId(): String? = auth.currentUser?.uid
}
