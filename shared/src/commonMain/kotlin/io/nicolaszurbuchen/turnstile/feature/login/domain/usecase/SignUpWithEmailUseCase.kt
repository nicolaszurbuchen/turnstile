package io.nicolaszurbuchen.turnstile.feature.login.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.login.domain.repository.UserIdentityRepository

class SignUpWithEmailUseCase(
    private val userIdentityRepository: UserIdentityRepository,
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
    ) {
        userIdentityRepository.signUpWithEmail(
            username = username,
            email = email,
            password = password,
        )
    }
}
