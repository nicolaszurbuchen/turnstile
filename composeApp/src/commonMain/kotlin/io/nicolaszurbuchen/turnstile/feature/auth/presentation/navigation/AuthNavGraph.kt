package io.nicolaszurbuchen.turnstile.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword.ForgotPasswordRoute
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin.SignInRoute
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup.SignUpRoute
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome.WelcomeRoute
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onAuthenticated: () -> Unit,
) {
    navigation<AuthGraph>(startDestination = WelcomeDestination) {
        composable<WelcomeDestination> {
            WelcomeRoute(
                viewModel = koinViewModel(),
                onNavigateToSignIn = { navController.navigate(SignInDestination) },
                onNavigateToSignUp = { navController.navigate(SignUpDestination) },
            )
        }
        composable<SignInDestination> {
            SignInRoute(
                viewModel = koinViewModel(),
                onLoggedIn = onAuthenticated,
                onNavigateToSignUp = {
                    navController.navigate(SignUpDestination, navOptions {
                        popUpTo(SignInDestination) { inclusive = true }
                    })
                },
                onNavigateToForgotPassword = { navController.navigate(ForgotPasswordDestination) },
                onNavigateBack = { navController.navigateUp() },
            )
        }
        composable<SignUpDestination> {
            SignUpRoute(
                viewModel = koinViewModel(),
                onRegistered = onAuthenticated,
                onNavigateToSignIn = {
                    navController.navigate(SignInDestination, navOptions {
                        popUpTo(SignUpDestination) { inclusive = true }
                    })
                },
                onNavigateBack = { navController.navigateUp() },
            )
        }
        composable<ForgotPasswordDestination> {
            ForgotPasswordRoute(
                viewModel = koinViewModel(),
                onNavigateBack = { navController.navigateUp() },
            )
        }
    }
}
