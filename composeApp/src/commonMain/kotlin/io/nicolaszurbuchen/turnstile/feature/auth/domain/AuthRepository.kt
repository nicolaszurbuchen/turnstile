package io.nicolaszurbuchen.turnstile.feature.auth.domain

/**
 * Auth repository contract.
 *
 * In real code this would live in your data layer (interface in domain,
 * implementation in data). Stubbed here so the example compiles.
 */
interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
    ): String
}
