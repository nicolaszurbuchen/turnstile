package io.nicolaszurbuchen.turnstile.feature.login.di

import io.nicolaszurbuchen.turnstile.feature.login.data.repository.UserIdentityRepositoryImpl
import io.nicolaszurbuchen.turnstile.feature.login.domain.repository.UserIdentityRepository
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SendPasswordResetEmailUseCase
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignInWithEmailUseCase
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignUpWithEmailUseCase
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.ForgotPasswordViewModel
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin.SignInViewModel
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup.SignUpViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule =
    module {
        singleOf(::UserIdentityRepositoryImpl) bind UserIdentityRepository::class

        singleOf(::SignInWithEmailUseCase)
        singleOf(::SignUpWithEmailUseCase)
        singleOf(::SendPasswordResetEmailUseCase)

        viewModelOf(::SignInViewModel)
        viewModelOf(::SignUpViewModel)
        viewModelOf(::ForgotPasswordViewModel)
    }
