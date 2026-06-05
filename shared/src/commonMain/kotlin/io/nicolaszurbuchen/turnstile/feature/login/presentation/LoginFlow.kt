package io.nicolaszurbuchen.turnstile.feature.login.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.turnstile.feature.login.presentation.component.LoginContainer
import io.nicolaszurbuchen.turnstile.feature.login.presentation.navigation.WelcomeDestination
import io.nicolaszurbuchen.turnstile.feature.login.presentation.navigation.loginGraph

@Composable
fun LoginFlow(onAuthenticated: () -> Unit) {
    val navController = rememberNavController()

    LoginContainer {
        NavHost(
            navController = navController,
            startDestination = WelcomeDestination,
        ) {
            loginGraph(
                navController = navController,
                onAuthenticated = onAuthenticated,
            )
        }
    }
}
