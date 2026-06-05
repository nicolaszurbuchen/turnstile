package io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote

import io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote.dto.UserDto

interface UserRemoteDataSource {
    suspend fun createUser(user: UserDto)

    suspend fun getUser(uid: String): UserDto?
}
