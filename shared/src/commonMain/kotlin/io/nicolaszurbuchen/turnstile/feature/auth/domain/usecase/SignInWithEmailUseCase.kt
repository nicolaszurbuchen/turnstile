package io.nicolaszurbuchen.turnstile.feature.auth.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.auth.domain.model.User
import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository

class SignInWithEmailUseCase(
    private val userIdentityRepository: UserIdentityRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): User = userIdentityRepository.signInWithEmail(email, password)
}
