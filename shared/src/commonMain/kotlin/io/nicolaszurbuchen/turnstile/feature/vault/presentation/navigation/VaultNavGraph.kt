package io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail.CredentialDetailRoute
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor.CredentialEditorRoute
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list.CredentialListRoute

fun NavGraphBuilder.vaultGraph(
    navController: NavController,
    onSignOut: () -> Unit,
) {
    navigation<VaultGraph>(startDestination = ListDestination) {
        composable<ListDestination> {
            CredentialListRoute(
                onNavigateToDetail = { id -> navController.navigate(DetailDestination(id)) },
                onNavigateToCreate = { navController.navigate(EditorDestination()) },
                onNavigateToAuth = onSignOut,
            )
        }

        composable<DetailDestination> {
            CredentialDetailRoute(
                onNavigateToEdit = { id -> navController.navigate(EditorDestination(id)) },
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable<EditorDestination>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(400),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(400),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(400),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(400),
                )
            },
        ) {
            CredentialEditorRoute(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
