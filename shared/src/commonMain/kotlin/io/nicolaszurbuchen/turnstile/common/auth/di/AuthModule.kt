package io.nicolaszurbuchen.turnstile.common.auth.di

import io.nicolaszurbuchen.turnstile.common.auth.data.datasource.remote.AuthRemoteDataSource
import io.nicolaszurbuchen.turnstile.common.auth.data.datasource.remote.AuthRemoteDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule =
    module {
        singleOf(::AuthRemoteDataSourceImpl) bind AuthRemoteDataSource::class
    }
