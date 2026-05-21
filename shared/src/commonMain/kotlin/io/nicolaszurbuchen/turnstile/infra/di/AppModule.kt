package io.nicolaszurbuchen.turnstile.infra.di

import io.nicolaszurbuchen.turnstile.common.session.di.sessionModule
import io.nicolaszurbuchen.turnstile.feature.auth.di.authModule
import io.nicolaszurbuchen.turnstile.feature.splash.di.splashModule
import io.nicolaszurbuchen.turnstile.feature.vault.di.vaultModule
import io.nicolaszurbuchen.turnstile.infra.network.firebaseModule

val appModule =
    listOf(
        authModule,
        firebaseModule,
        vaultModule,
        sessionModule,
        splashModule,
    )
