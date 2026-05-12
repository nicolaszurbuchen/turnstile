package io.nicolaszurbuchen.turnstile.core.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.navigation.AuthGraph
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.navigation.authGraph
import io.nicolaszurbuchen.turnstile.feature.home.presentation.navigation.HomeGraph
import io.nicolaszurbuchen.turnstile.feature.home.presentation.navigation.homeGraph

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AuthGraph,
    ) {
        authGraph(
            navController = navController,
            onAuthenticated = {
                navController.navigate(HomeGraph) {
                    popUpTo(AuthGraph) { inclusive = true }
                }
            },
        )
        homeGraph(navController = navController)
    }
}
