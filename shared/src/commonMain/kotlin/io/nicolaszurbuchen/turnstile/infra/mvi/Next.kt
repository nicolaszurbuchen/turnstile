package io.nicolaszurbuchen.turnstile.infra.mvi

/**
 * Result of a single reducer invocation.
 *
 * Bundles the new state with any commands to execute and events to emit.
 * Treating commands and events as data (rather than running them inside
 * the reducer) keeps the reducer pure and trivially testable.
 *
 * @param state The new state after applying the trigger.
 * @param commands Async work descriptions to execute. The base ViewModel will hand each to executeCommand().
 * @param events One-shot UI events to emit.
 */
data class Next<S, C : Command, E : Event>(
    val state: S,
    val commands: List<C> = emptyList(),
    val events: List<E> = emptyList(),
)
