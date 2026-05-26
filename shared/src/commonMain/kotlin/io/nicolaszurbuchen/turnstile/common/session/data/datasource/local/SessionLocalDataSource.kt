package io.nicolaszurbuchen.turnstile.common.session.data.datasource.local

interface SessionLocalDataSource {
    fun getCurrentUserId(): String?
}