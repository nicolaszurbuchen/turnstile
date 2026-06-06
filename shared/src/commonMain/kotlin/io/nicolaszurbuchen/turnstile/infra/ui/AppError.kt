package io.nicolaszurbuchen.turnstile.infra.ui

/** Lightweight value type for presenting an error to the user. */
data class AppError(
    val message: String,
    val cause: Throwable? = null,
)
