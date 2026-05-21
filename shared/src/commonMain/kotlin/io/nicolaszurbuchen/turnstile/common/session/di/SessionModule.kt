package io.nicolaszurbuchen.turnstile.common.session.di

import io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory.SessionLocalDataSource
import io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory.SessionFirebaseLocalDataSourceImpl
import io.nicolaszurbuchen.turnstile.common.session.data.repository.SessionRepositoryImpl
import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sessionModule =
    module {
        singleOf(::SessionFirebaseLocalDataSourceImpl) bind SessionLocalDataSource::class
        singleOf(::SessionRepositoryImpl) bind SessionRepository::class
    }
