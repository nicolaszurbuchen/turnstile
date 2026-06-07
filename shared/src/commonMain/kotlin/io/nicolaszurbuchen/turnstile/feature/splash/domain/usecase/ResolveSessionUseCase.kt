package io.nicolaszurbuchen.turnstile.feature.splash.domain.usecase

import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

class ResolveSessionUseCase(
    private val sessionRepository: SessionRepository,
) {
    suspend operator fun invoke(): SessionStatus =
        sessionRepository.resolveSessionStatus()
}
