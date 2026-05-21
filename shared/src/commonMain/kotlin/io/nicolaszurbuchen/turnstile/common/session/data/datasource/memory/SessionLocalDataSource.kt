package io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory

interface SessionLocalDataSource {
    fun getCurrentUserId(): String?
}
