package io.nicolaszurbuchen.turnstile.common.session.di

import io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory.SessionMemoryDataSource
import io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory.SessionMemoryDataSourceImpl
import io.nicolaszurbuchen.turnstile.common.session.data.repository.SessionRepositoryImpl
import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sessionModule =
    module {
        singleOf(::SessionMemoryDataSourceImpl) bind SessionMemoryDataSource::class
        singleOf(::SessionRepositoryImpl) bind SessionRepository::class
    }
