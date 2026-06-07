package io.nicolaszurbuchen.turnstile.common.session.domain.repository

import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

interface SessionRepository {
    fun getCurrentUserId(): String?

    suspend fun resolveSessionStatus(): SessionStatus

    suspend fun signOut()
}
