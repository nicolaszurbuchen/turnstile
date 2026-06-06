package io.nicolaszurbuchen.turnstile.feature.vault.di

import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.CredentialRemoteDataSource
import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.CredentialRemoteDataSourceImpl
import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.mapper.CredentialMapper
import io.nicolaszurbuchen.turnstile.feature.vault.data.repository.CredentialRepositoryImpl
import io.nicolaszurbuchen.turnstile.feature.vault.domain.repository.CredentialRepository
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.DeleteCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialsUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.SaveCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.SignOutUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail.CredentialDetailViewModel
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor.CredentialEditorViewModel
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list.CredentialListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val vaultModule =
    module {
        // Data Sources
        singleOf(::CredentialRemoteDataSourceImpl) bind CredentialRemoteDataSource::class

        // Mappers
        singleOf(::CredentialMapper)

        // Repositories
        singleOf(::CredentialRepositoryImpl) bind CredentialRepository::class

        // Use Cases
        singleOf(::DeleteCredentialUseCase)
        singleOf(::GetCredentialsUseCase)
        singleOf(::GetCredentialUseCase)
        singleOf(::SaveCredentialUseCase)
        singleOf(::SignOutUseCase)

        // View Models
        viewModelOf(::CredentialListViewModel)
        viewModelOf(::CredentialDetailViewModel)
        viewModelOf(::CredentialEditorViewModel)
    }
