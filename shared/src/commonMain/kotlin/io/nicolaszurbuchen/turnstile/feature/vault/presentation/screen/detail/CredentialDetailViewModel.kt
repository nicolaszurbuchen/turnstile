package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.DeleteCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.presentation.navigation.DetailDestination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class CredentialDetailViewModel(
    savedStateHandle: SavedStateHandle,
    storeFactory: StoreFactory,
    getCredential: GetCredentialUseCase,
    deleteCredential: DeleteCredentialUseCase,
) : ViewModel() {
    private val credentialId = savedStateHandle.toRoute<DetailDestination>().id
    private val store = CredentialDetailStoreFactory(
        storeFactory = storeFactory,
        getCredential = getCredential,
        deleteCredential = deleteCredential,
        credentialId = credentialId,
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CredentialDetailState> = store.stateFlow

    val labels: Flow<CredentialDetailLabel> = store.labels

    fun onIntent(intent: CredentialDetailIntent) {
        store.accept(intent)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}
