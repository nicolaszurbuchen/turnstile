package io.nicolaszurbuchen.turnstile.feature.splash.domain.usecase

import io.nicolaszurbuchen.turnstile.common.session.domain.repository.SessionRepository
import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus

class ResolveSessionUseCase(
    private val sessionRepository: SessionRepository,
) {
    operator fun invoke(): SessionStatus {
        val userId = sessionRepository.getCurrentUserId()
        println("ResolveSessionUseCase - userId: $userId")
        return if (userId != null) SessionStatus.Authenticated
        else SessionStatus.Unauthenticated
    }
}