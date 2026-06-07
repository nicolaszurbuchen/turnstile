package io.nicolaszurbuchen.turnstile.common.session.data.datasource.local

import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first

class SessionLocalDataSourceImpl(
    private val auth: FirebaseAuth,
) : SessionLocalDataSource {
    override fun getCurrentUserId(): String? = auth.currentUser?.uid

    override suspend fun awaitCurrentUserId(): String? =
        auth.authStateChanged.first()?.uid
}
