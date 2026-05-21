package io.nicolaszurbuchen.turnstile.common.session.data.datasource.memory

import dev.gitlive.firebase.auth.FirebaseAuth

class SessionFirebaseLocalDataSourceImpl(
    private val auth: FirebaseAuth,
) : SessionLocalDataSource {
    override fun getCurrentUserId(): String? = auth.currentUser?.uid
}
