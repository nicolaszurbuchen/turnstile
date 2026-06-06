package io.nicolaszurbuchen.turnstile.feature.login.data.repository

import io.nicolaszurbuchen.turnstile.common.auth.data.datasource.remote.AuthRemoteDataSource
import io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote.UserRemoteDataSource
import io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote.dto.UserDto
import io.nicolaszurbuchen.turnstile.feature.login.domain.repository.UserIdentityRepository

class UserIdentityRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserIdentityRepository {
    override suspend fun signInWithEmail(
        email: String,
        password: String,
    ) {
        authRemoteDataSource.signInWithEmail(email, password)
    }

    override suspend fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
    ) {
        val uid = authRemoteDataSource.signUpWithEmail(email, password)
        val userDto = UserDto(id = uid, email = email, username = username)

        try {
            userRemoteDataSource.createUser(userDto)
        } catch (e: Exception) {
            authRemoteDataSource.deleteAccount()
            throw e
        }
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        authRemoteDataSource.sendPasswordResetEmail(email)
    }
}
