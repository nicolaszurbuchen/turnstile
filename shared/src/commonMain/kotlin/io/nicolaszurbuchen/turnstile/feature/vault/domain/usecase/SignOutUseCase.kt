package io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.login.domain.repository.UserIdentityRepository

class SignOutUseCase(
    private val userIdentityRepository: UserIdentityRepository,
) {
    suspend operator fun invoke() = userIdentityRepository.signOut()
}
