package io.nicolaszurbuchen.turnstile.feature.home.di

import io.nicolaszurbuchen.turnstile.feature.home.data.FakePasswordRepository
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.PasswordRepository
import io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule =
    module {
        single<PasswordRepository> { FakePasswordRepository() }
        viewModel { DashboardViewModel(get()) }
    }
