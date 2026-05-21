package io.nicolaszurbuchen.turnstile.feature.home.di

import io.nicolaszurbuchen.turnstile.feature.home.data.datasource.remote.CredentialRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.home.data.datasource.remote.CredentialRemoteDataSourceImpl
import io.nicolaszurbuchen.turnstile.feature.home.data.repository.CredentialRepositoryImpl
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.CredentialRepository
import io.nicolaszurbuchen.turnstile.feature.home.domain.usecase.DeleteCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.home.domain.usecase.GetCredentialsUseCase
import io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard.DashboardViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule =
    module {
        // Data Sources
        singleOf(::CredentialRemoteDataSourceImpl) bind CredentialRemoteDataSource::class

        // Repositories
        singleOf(::CredentialRepositoryImpl) bind CredentialRepository::class

        // Use Cases
        singleOf(::GetCredentialsUseCase)
        singleOf(::DeleteCredentialUseCase)

        // View Models
        viewModelOf(::DashboardViewModel)
    }
