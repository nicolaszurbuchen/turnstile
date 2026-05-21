package io.nicolaszurbuchen.turnstile.feature.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val username: String?,
)
