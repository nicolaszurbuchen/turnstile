package io.nicolaszurbuchen.turnstile.feature.auth.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository

class SendPasswordResetEmailUseCase(
    private val userIdentityRepository: UserIdentityRepository,
) {
    suspend operator fun invoke(email: String) = userIdentityRepository.sendPasswordResetEmail(email)
}
