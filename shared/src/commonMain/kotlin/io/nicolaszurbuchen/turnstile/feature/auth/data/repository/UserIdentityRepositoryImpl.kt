package io.nicolaszurbuchen.turnstile.feature.auth.data.repository

import io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote.AuthRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote.UserRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.auth.domain.exception.UserNotFoundException
import io.nicolaszurbuchen.turnstile.feature.auth.domain.model.User
import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository

class UserIdentityRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserIdentityRepository {

    override suspend fun signInWithEmail(email: String, password: String): User {
        val firebaseUser = authRemoteDataSource.signInWithEmail(email, password)
        return userRemoteDataSource.getUser(firebaseUser.uid)
            ?: throw UserNotFoundException("User document not found for uid: ${firebaseUser.uid}")
    }

    override suspend fun signUpWithEmail(username: String, email: String, password: String): User {
        val firebaseUser = authRemoteDataSource.signUpWithEmail(email, password)
        val user = User(
            id = firebaseUser.uid,
            email = firebaseUser.email ?: email,
            username = username
        )
        try {
            userRemoteDataSource.createUser(user)
        } catch (e: Exception) {
            authRemoteDataSource.deleteAccount()
            throw e
        }
        return user
    }

    override suspend fun signInWithGoogle(): User {
        val firebaseUser = authRemoteDataSource.signInWithGoogle()
        return userRemoteDataSource.getUser(firebaseUser.uid)
            ?: User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                username = firebaseUser.displayName
            )
    }

    override suspend fun signInWithApple(): User {
        val firebaseUser = authRemoteDataSource.signInWithApple()
        return userRemoteDataSource.getUser(firebaseUser.uid)
            ?: User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                username = firebaseUser.displayName
            )
    }

    override suspend fun signOut() {
        authRemoteDataSource.signOut()
    }

    override fun getCurrentUserId(): String? {
        return authRemoteDataSource.getCurrentUserId()
    }
}
