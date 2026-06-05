package io.nicolaszurbuchen.turnstile.common.user.di

import io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote.UserRemoteDataSource
import io.nicolaszurbuchen.turnstile.common.user.data.datasource.remote.UserRemoteDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule =
    module {
        singleOf(::UserRemoteDataSourceImpl) bind UserRemoteDataSource::class
    }
