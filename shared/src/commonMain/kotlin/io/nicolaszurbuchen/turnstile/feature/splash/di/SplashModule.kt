package io.nicolaszurbuchen.turnstile.feature.splash.di

import io.nicolaszurbuchen.turnstile.feature.splash.domain.usecase.ResolveSessionUseCase
import io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen.SplashStoreFactory
import io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen.SplashViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashModule =
    module {
        factoryOf(::ResolveSessionUseCase)

        factoryOf(::SplashStoreFactory)

        viewModelOf(::SplashViewModel)
    }
