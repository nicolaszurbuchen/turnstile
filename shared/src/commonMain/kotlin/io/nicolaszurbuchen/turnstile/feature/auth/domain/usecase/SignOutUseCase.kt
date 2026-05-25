package io.nicolaszurbuchen.turnstile.feature.auth.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository

class SignOutUseCase(
    private val userIdentityRepository: UserIdentityRepository,
) {
    suspend operator fun invoke() = userIdentityRepository.signOut()
}
