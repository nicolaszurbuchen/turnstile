package io.nicolaszurbuchen.turnstile.feature.login.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.ForgotPasswordRoute
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin.SignInRoute
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup.SignUpRoute
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.welcome.WelcomeRoute

fun NavGraphBuilder.loginGraph(
    navController: NavController,
    onAuthenticated: () -> Unit,
) {
    composable<WelcomeDestination> {
        WelcomeRoute(
            onNavigateToSignIn = { navController.navigate(SignInDestination) },
            onNavigateToSignUp = { navController.navigate(SignUpDestination) },
        )
    }
    composable<SignInDestination> {
        SignInRoute(
            onNavigateHome = onAuthenticated,
            onNavigateToSignUp = {
                navController.navigate(
                    SignUpDestination,
                    navOptions {
                        popUpTo(SignInDestination) { inclusive = true }
                    },
                )
            },
            onNavigateToForgotPassword = { navController.navigate(ForgotPasswordDestination) },
            onNavigateBack = { navController.navigateUp() },
        )
    }
    composable<SignUpDestination> {
        SignUpRoute(
            onNavigateHome = onAuthenticated,
            onNavigateToSignIn = {
                navController.navigate(
                    SignInDestination,
                    navOptions {
                        popUpTo(SignUpDestination) { inclusive = true }
                    },
                )
            },
            onNavigateBack = { navController.navigateUp() },
        )
    }
    composable<ForgotPasswordDestination> {
        ForgotPasswordRoute(
            onNavigateBack = { navController.navigateUp() },
        )
    }
}
