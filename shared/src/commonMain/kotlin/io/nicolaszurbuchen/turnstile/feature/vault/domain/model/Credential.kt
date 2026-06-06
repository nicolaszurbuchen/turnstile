package io.nicolaszurbuchen.turnstile.feature.vault.domain.model

data class Credential(
    val id: String,
    val title: String,
    val username: String,
    val password: String,
    val memo: String?,
)
