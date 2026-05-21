package io.nicolaszurbuchen.turnstile.common.session.data.repository

import io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory.SessionLocalDataSource
import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val localDataSource: SessionLocalDataSource,
) : SessionRepository {

    override fun getCurrentUserId(): String? {
        return localDataSource.getCurrentUserId()
    }
}
