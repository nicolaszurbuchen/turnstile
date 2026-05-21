package io.nicolaszurbuchen.turnstile.feature.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Credential(
    val id: String,
    val name: String,
    val type: String,
    val data: String,
)
