package io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase

import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository

class SignOutUseCase(
    private val sessionRepository: SessionRepository,
) {
    suspend operator fun invoke() = sessionRepository.signOut()
}
