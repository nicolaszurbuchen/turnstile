package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation.EditorDestination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class CredentialEditorViewModel(
    savedStateHandle: SavedStateHandle,
    factory: CredentialEditorStoreFactory,
) : ViewModel() {
    private val credentialId = savedStateHandle.toRoute<EditorDestination>().id
    private val store = factory.create(credentialId = credentialId)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CredentialEditorState> = store.stateFlow

    val labels: Flow<CredentialEditorLabel> = store.labels

    fun onIntent(intent: CredentialEditorIntent) {
        store.accept(intent)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}
