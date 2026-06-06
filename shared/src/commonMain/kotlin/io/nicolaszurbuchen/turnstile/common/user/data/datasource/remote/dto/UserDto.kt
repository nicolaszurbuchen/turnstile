package io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val username: String?,
)
