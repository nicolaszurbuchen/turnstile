package io.nicolaszurbuchen.turnstile.common.session.data.repository

import io.nicolaszurbuchen.turnstile.common.auth.data.datasource.remote.AuthRemoteDataSource
import io.nicolaszurbuchen.turnstile.common.session.data.datasource.local.SessionLocalDataSource
import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

class SessionRepositoryImpl(
    private val localDataSource: SessionLocalDataSource,
    private val remoteDataSource: AuthRemoteDataSource,
) : SessionRepository {
    override fun getCurrentUserId(): String? = localDataSource.getCurrentUserId()

    override suspend fun resolveSessionStatus(): SessionStatus =
        localDataSource.awaitCurrentUserId()
            ?.let { SessionStatus.Authenticated }
            ?: SessionStatus.Unauthenticated

    override suspend fun signOut() {
        remoteDataSource.signOut()
    }
}
