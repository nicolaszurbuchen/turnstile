package io.nicolaszurbuchen.turnstile.feature.home.data

import io.nicolaszurbuchen.turnstile.feature.home.domain.model.PasswordEntry
import io.nicolaszurbuchen.turnstile.feature.home.domain.repository.PasswordRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakePasswordRepository : PasswordRepository {
    private val store =
        mutableListOf(
            PasswordEntry("1", "Personal Gmail", "eric.lambert@gmail.com", "hunter2!Secure"),
            PasswordEntry("2", "GitHub", "elambert", "gh_pat_abc123secret"),
            PasswordEntry("3", "Netflix", "eric.lambert@gmail.com", "N3tfl1xPass!"),
            PasswordEntry("4", "Apple ID", "eric.lambert@icloud.com", "Appl3Secure!9"),
            PasswordEntry("5", "LinkedIn", "eric.lambert@work.com", "L1nk3dIn#Pass"),
            PasswordEntry("6", "AWS Console", "eric.lambert@company.io", "AwsC0ns0le\$24"),
            PasswordEntry("7", "Figma", "eric@design.co", "F1gmaR0cks!"),
            PasswordEntry("8", "Notion", "eric.lambert@gmail.com", "Not10nApp!Pass"),
            PasswordEntry("9", "Spotify", "eric.lambert@gmail.com", "Sp0t1fyPass#9"),
            PasswordEntry("10", "Twitter / X", "eric_lambert_dev", "Tw1tt3r!X2024"),
        )

    override suspend fun getEntries(): List<PasswordEntry> {
        delay(1_500)
        return store.toList()
    }

    override suspend fun refresh() {
        delay(1_200)
        if (Random.nextFloat() < 0.3f) {
            error("Could not reach server. Check your connection and try again.")
        }
    }

    override suspend fun deleteEntry(id: String) {
        delay(300)
        store.removeAll { it.id == id }
    }
}
