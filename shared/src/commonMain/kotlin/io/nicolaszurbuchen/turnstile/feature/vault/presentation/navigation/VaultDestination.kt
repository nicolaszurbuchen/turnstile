package io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface VaultDestination

@Serializable
data object VaultGraph : VaultDestination

@Serializable
internal data object ListDestination : VaultDestination

@Serializable
internal data class DetailDestination(val id: String) : VaultDestination

@Serializable
internal data class EditorDestination(val id: String? = null) : VaultDestination
