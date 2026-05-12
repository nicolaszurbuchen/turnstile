package io.nicolaszurbuchen.turnstile.core.di

import io.nicolaszurbuchen.turnstile.feature.auth.di.authModule
import io.nicolaszurbuchen.turnstile.feature.home.di.homeModule

val appModule = listOf(
    authModule,
    homeModule,
)