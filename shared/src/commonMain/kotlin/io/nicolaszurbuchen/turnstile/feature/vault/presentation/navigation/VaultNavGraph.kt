package io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail.CredentialDetailRoute
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor.CredentialEditorRoute
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list.CredentialListRoute

fun NavGraphBuilder.vaultGraph(
    navController: NavController,
) {
    navigation<VaultGraph>(startDestination = ListDestination) {
        composable<ListDestination> {
            CredentialListRoute(
                onNavigateToDetail = { id -> navController.navigate(DetailDestination(id)) },
                onNavigateToCreate = { navController.navigate(EditorDestination()) },
            )
        }

        composable<DetailDestination> {
            CredentialDetailRoute(
                onNavigateToEdit = { id -> navController.navigate(EditorDestination(id)) },
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable<EditorDestination> {
            CredentialEditorRoute(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
