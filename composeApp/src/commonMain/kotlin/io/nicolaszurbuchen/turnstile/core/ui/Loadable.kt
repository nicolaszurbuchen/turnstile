package io.nicolaszurbuchen.turnstile.core.ui

import io.nicolaszurbuchen.turnstile.core.mvi.Command
import io.nicolaszurbuchen.turnstile.core.mvi.Event
import io.nicolaszurbuchen.turnstile.core.mvi.Next
import io.nicolaszurbuchen.turnstile.core.mvi.State

/** Lightweight value type for presenting an error to the user. */
data class AppError(val message: String, val cause: Throwable? = null)

/**
 * Represents the loading lifecycle of a screen.
 *
 * Implements [State] so that `Loadable<T>` can be used directly as the `S` type parameter
 * in [io.nicolaszurbuchen.turnstile.core.mvi.MviViewModel] without modifying the base class.
 */
sealed interface Loadable<out T> {
    data object Loading : Loadable<Nothing>
    data class Failure(val error: AppError) : Loadable<Nothing>
    data class Success<T>(
        val data: T,
        val refreshing: Boolean = false,
        val refreshError: AppError? = null,
    ) : Loadable<T>
}

/**
 * Reducer helper — runs [block] only when this is [Loadable.Success]; returns the unchanged
 * state otherwise. Eliminates the repeated `if not Success, ignore` pattern in reducers.
 */
fun <S, C : Command, E : Event> Loadable<S>.onSuccess(
    block: (S) -> Next<Loadable<S>, C, E>,
): Next<Loadable<S>, C, E> =
    if (this is Loadable.Success) block(data) else Next(state = this)
