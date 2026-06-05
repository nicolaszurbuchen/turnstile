package io.nicolaszurbuchen.turnstile.common.auth.data.datasource.remote

import dev.gitlive.firebase.auth.FirebaseAuth

class AuthRemoteDataSourceImpl(
    private val auth: FirebaseAuth,
) : AuthRemoteDataSource {
    override suspend fun signInWithEmail(
        email: String,
        password: String,
    ) {
        val result = auth.signInWithEmailAndPassword(email, password)
        result.user ?: throw Exception("Sign in failed: User is null")
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
    ): String {
        val result = auth.createUserWithEmailAndPassword(email, password)
        val user = result.user ?: throw Exception("Sign up failed: User is null")
        return user.uid
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteAccount() {
        auth.currentUser?.delete()
    }
}
