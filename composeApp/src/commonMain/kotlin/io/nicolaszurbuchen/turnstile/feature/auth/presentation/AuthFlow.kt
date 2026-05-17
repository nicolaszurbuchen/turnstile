package io.nicolaszurbuchen.turnstile.feature.auth.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthContainer
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.navigation.WelcomeDestination
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.navigation.authGraph

@Composable
fun AuthFlow(onAuthenticated: () -> Unit) {
    val navController = rememberNavController()

    AuthContainer {
        NavHost(
            navController = navController,
            startDestination = WelcomeDestination,
        ) {
            authGraph(
                navController = navController,
                onAuthenticated = onAuthenticated,
            )
        }
    }
}
