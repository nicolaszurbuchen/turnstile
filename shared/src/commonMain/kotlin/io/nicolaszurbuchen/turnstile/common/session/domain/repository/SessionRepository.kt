package io.nicolaszurbuchen.turnstile.common.session.domain.repository

interface SessionRepository {
    fun currentUserId(): String?
}
