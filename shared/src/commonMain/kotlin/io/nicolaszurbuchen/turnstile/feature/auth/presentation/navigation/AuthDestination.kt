package io.nicolaszurbuchen.turnstile.feature.auth.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthDestination

@Serializable data object AuthGraph : AuthDestination

@Serializable internal data object WelcomeDestination : AuthDestination

@Serializable internal data object SignInDestination : AuthDestination

@Serializable internal data object SignUpDestination : AuthDestination

@Serializable internal data object ForgotPasswordDestination : AuthDestination
