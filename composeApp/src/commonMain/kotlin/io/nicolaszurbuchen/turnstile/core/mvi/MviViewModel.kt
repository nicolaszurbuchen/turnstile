package io.nicolaszurbuchen.turnstile.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<S, T, I : Intent, A : Action, C : Command, E : Event>(
    initialState: S,
    private val reducer: Reducer<S, T, C, E>,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _events = Channel<E>(Channel.BUFFERED)
    val events: Flow<E> = _events.receiveAsFlow()

    // UNLIMITED so trySend never suspends or fails on UI-thread dispatch.
    private val triggers = Channel<T>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            triggers.consumeAsFlow().collect { trigger ->
                val next = reducer.reduce(_state.value, trigger)
                _state.value = next.state
                next.events.forEach { _events.trySend(it) }
                next.commands.forEach { command ->
                    viewModelScope.launch { executeCommand(command) }
                }
            }
        }
        bootstrap()?.let { sendIntent(it) }
    }

    /**
     * Optional initial intent to dispatch when the ViewModel is constructed.
     * Override to trigger initial data loads or similar startup behavior.
     */
    protected open fun bootstrap(): I? = null

    /**
     * Execute a command description. Typically launches a coroutine, does I/O,
     * and dispatches the result back as an action via dispatchAction().
     */
    protected abstract suspend fun executeCommand(command: C)

    /** Public entry point — the UI sends intents through this. */
    @Suppress("UNCHECKED_CAST")
    fun sendIntent(intent: I) {
        // Safe by construction: I is a nested subtype of T per the screen's
        // sealed Trigger hierarchy.
        triggers.trySend(intent as T)
    }

    /**
     * Internal entry point — only the command executor (running inside this
     * ViewModel) should dispatch actions. Protected visibility ensures the
     * UI cannot fake system results.
     */
    @Suppress("UNCHECKED_CAST")
    protected fun dispatchAction(action: A) {
        // Safe by construction: A is a nested subtype of T per the screen's
        // sealed Trigger hierarchy.
        triggers.trySend(action as T)
    }
}