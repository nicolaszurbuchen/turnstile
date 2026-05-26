package io.nicolaszurbuchen.turnstile.common.session.data.datasource.local

import dev.gitlive.firebase.auth.FirebaseAuth

class SessionLocalDataSourceImpl(
    private val auth: FirebaseAuth,
) : SessionLocalDataSource {
    override fun getCurrentUserId(): String? = auth.currentUser?.uid
}
