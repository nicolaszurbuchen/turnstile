package io.nicolaszurbuchen.turnstile.feature.login.di

import io.nicolaszurbuchen.turnstile.feature.login.data.repository.UserIdentityRepositoryImpl
import io.nicolaszurbuchen.turnstile.feature.login.domain.repository.UserIdentityRepository
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SendPasswordResetEmailUseCase
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignInWithEmailUseCase
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignUpWithEmailUseCase
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.ForgotPasswordStoreFactory
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword.ForgotPasswordViewModel
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin.SignInStoreFactory
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin.SignInViewModel
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup.SignUpStoreFactory
import io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup.SignUpViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule =
    module {
        singleOf(::UserIdentityRepositoryImpl) bind UserIdentityRepository::class

        factoryOf(::SignInWithEmailUseCase)
        factoryOf(::SignUpWithEmailUseCase)
        factoryOf(::SendPasswordResetEmailUseCase)

        factoryOf(::SignInStoreFactory)
        factoryOf(::SignUpStoreFactory)
        factoryOf(::ForgotPasswordStoreFactory)

        viewModelOf(::SignInViewModel)
        viewModelOf(::SignUpViewModel)
        viewModelOf(::ForgotPasswordViewModel)
    }
