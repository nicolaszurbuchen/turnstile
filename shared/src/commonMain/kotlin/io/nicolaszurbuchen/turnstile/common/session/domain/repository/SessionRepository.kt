package io.nicolaszurbuchen.turnstile.common.session.domain.repository

interface SessionRepository {
    fun getCurrentUserId(): String?

    suspend fun signOut()
}
