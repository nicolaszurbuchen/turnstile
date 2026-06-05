package io.nicolaszurbuchen.turnstile.feature.login.domain.usecase

import io.nicolaszurbuchen.turnstile.feature.login.domain.repository.UserIdentityRepository

class SignInWithEmailUseCase(
    private val userIdentityRepository: UserIdentityRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ) {
        userIdentityRepository.signInWithEmail(email, password)
    }
}
