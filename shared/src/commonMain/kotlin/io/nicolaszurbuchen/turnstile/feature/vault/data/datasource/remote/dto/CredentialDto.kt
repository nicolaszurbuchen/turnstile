package io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CredentialDto(
    val id: String,
    val title: String,
    val username: String,
    val password: String,
    val memo: String?,
)
