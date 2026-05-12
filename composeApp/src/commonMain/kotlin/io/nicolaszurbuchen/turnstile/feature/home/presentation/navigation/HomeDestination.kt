package io.nicolaszurbuchen.turnstile.feature.home.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeDestination

@Serializable data object HomeGraph : HomeDestination
@Serializable internal data object DashboardDestination : HomeDestination
