package io.nicolaszurbuchen.turnstile.feature.auth.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote.AuthRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote.AuthRemoteDataSourceImpl
import io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote.UserRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.auth.data.datasource.remote.UserRemoteDataSourceImpl
import io.nicolaszurbuchen.turnstile.feature.auth.data.repository.UserIdentityRepositoryImpl
import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository
import io.nicolaszurbuchen.turnstile.feature.auth.domain.usecase.SignOutUseCase
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.forgotpassword.ForgotPasswordViewModel
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signin.SignInViewModel
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup.SignUpViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule =
    module {
        // Data Sources
        singleOf(::AuthRemoteDataSourceImpl) bind AuthRemoteDataSource::class
        singleOf(::UserRemoteDataSourceImpl) bind UserRemoteDataSource::class

        // Repositories
        singleOf(::UserIdentityRepositoryImpl) bind UserIdentityRepository::class

        // Use Cases
        singleOf(::SignOutUseCase)

        viewModelOf(::SignInViewModel)
        viewModelOf(::SignUpViewModel)
        viewModelOf(::ForgotPasswordViewModel)
    }
