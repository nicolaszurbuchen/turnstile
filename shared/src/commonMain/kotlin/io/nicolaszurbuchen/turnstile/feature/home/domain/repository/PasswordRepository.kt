package io.nicolaszurbuchen.turnstile.feature.home.domain.repository

import io.nicolaszurbuchen.turnstile.feature.home.domain.model.PasswordEntry

interface PasswordRepository {
    suspend fun getEntries(): List<PasswordEntry>

    suspend fun refresh()

    suspend fun deleteEntry(id: String)
}
