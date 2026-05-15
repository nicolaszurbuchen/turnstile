package io.nicolaszurbuchen.turnstile.core.mvi

/**
 * Pure function: (state, trigger) -> new state + commands + events.
 *
 * Reducers must be pure. No coroutines, no I/O, no mutable external state.
 * Anything async belongs in the ViewModel's executeCommand() implementation.
 *
 * @param S The screen's state type.
 * @param T The screen's trigger type (typically a sealed interface containing both Intent and Action sub-hierarchies).
 * @param C The screen's command type.
 * @param E The screen's event type.
 */
fun interface Reducer<S, T , C : Command, E : Event> {
    fun reduce(state: S, trigger: T): Next<S, C, E>
}