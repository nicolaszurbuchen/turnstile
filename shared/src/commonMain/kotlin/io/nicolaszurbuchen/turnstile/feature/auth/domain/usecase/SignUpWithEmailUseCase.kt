package io.nicolaszurbuchen.turnstile.feature.auth.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.auth.domain.model.User
import io.nicolaszurbuchen.turnstile.feature.auth.domain.repository.UserIdentityRepository

class SignUpWithEmailUseCase(
    private val userIdentityRepository: UserIdentityRepository,
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
    ): User =
        userIdentityRepository.signUpWithEmail(
            username = username,
            email = email,
            password = password,
        )
}
