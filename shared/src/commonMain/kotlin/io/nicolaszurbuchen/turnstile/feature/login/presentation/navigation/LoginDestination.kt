package io.nicolaszurbuchen.turnstile.feature.login.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface LoginDestination

@Serializable data object LoginGraph : LoginDestination

@Serializable internal data object WelcomeDestination : LoginDestination

@Serializable internal data object SignInDestination : LoginDestination

@Serializable internal data object SignUpDestination : LoginDestination

@Serializable internal data object ForgotPasswordDestination : LoginDestination
