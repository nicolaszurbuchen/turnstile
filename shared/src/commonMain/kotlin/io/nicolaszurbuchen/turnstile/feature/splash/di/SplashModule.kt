package io.nicolaszurbuchen.turnstile.feature.splash.di

import io.nicolaszurbuchen.turnstile.feature.splash.domain.usecase.ResolveSessionUseCase
import io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen.SplashViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashModule =
    module {
        // Use Cases
        singleOf(::ResolveSessionUseCase)

        // View Models
        viewModelOf(::SplashViewModel)
    }