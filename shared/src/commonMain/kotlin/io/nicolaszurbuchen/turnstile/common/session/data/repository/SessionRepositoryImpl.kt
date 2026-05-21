package io.nicolaszurbuchen.turnstile.common.session.data.repository

import io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory.SessionMemoryDataSource
import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val memoryDataSource: SessionMemoryDataSource,
) : SessionRepository {
    override fun currentUserId(): String? = memoryDataSource.userId

    fun setCurrentUserId(userId: String?) {
        memoryDataSource.userId = userId
    }
}
