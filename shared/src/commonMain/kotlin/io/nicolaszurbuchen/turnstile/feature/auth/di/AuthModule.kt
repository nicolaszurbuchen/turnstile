package io.nicolaszurbuchen.turnstile.feature.auth.di

import io.nicolaszurbuchen.turnstile.feature.auth.domain.AuthRepository
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword.ForgotPasswordViewModel
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin.SignInViewModel
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule =
    module {
        // TODO: replace with real implementation once the data layer is ready
        single<AuthRepository> {
            object : AuthRepository {
                override suspend fun login(
                    email: String,
                    password: String,
                ): String = "stub_token"
            }
        }

        viewModel { SignInViewModel(get()) }
        viewModel { SignUpViewModel() }
        viewModel { ForgotPasswordViewModel() }
    }
