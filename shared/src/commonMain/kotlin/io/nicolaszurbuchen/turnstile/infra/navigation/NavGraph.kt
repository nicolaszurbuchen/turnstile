package io.nicolaszurbuchen.turnstile.infra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.nicolaszurbuchen.turnstile.feature.login.presentation.LoginFlow
import io.nicolaszurbuchen.turnstile.feature.login.presentation.navigation.LoginGraph
import io.nicolaszurbuchen.turnstile.feature.splash.presentation.navigation.SplashDestination
import io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen.SplashRoute
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation.VaultGraph
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation.vaultGraph
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination,
    ) {
        composable<SplashDestination> {
            SplashRoute(
                viewModel = koinViewModel(),
                onNavigateToAuth = {
                    navController.navigate(LoginGraph) {
                        popUpTo(SplashDestination) { inclusive = true }
                    }
                },
                onNavigateToVault = {
                    navController.navigate(VaultGraph) {
                        popUpTo(SplashDestination) { inclusive = true }
                    }
                },
            )
        }

        composable<LoginGraph> {
            LoginFlow(
                onAuthenticated = {
                    navController.navigate(VaultGraph) {
                        popUpTo(LoginGraph) { inclusive = true }
                    }
                },
            )
        }

        vaultGraph(
            navController = navController,
            onSignOut = {
                navController.navigate(LoginGraph) {
                    popUpTo(VaultGraph) { inclusive = true }
                }
            },
        )
    }
}
