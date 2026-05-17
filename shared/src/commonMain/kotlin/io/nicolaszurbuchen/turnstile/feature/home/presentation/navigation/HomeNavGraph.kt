package io.nicolaszurbuchen.turnstile.feature.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard.DashboardRoute
import io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard.DashboardViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation<HomeGraph>(startDestination = DashboardDestination) {
        composable<DashboardDestination> {
            DashboardRoute(viewModel = koinViewModel<DashboardViewModel>())
        }
    }
}
