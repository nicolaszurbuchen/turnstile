package io.nicolaszurbuchen.turnstile.infra.di

import io.nicolaszurbuchen.turnstile.common.session.di.sessionModule
import io.nicolaszurbuchen.turnstile.feature.auth.di.authModule
import io.nicolaszurbuchen.turnstile.feature.home.di.homeModule

val appModule =
    listOf(
        authModule,
        homeModule,
        sessionModule,
    )
