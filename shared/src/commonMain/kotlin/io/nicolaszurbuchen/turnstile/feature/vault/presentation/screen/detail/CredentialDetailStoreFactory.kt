package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.DeleteCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialUseCase
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import kotlinx.coroutines.launch

interface CredentialDetailStore : Store<CredentialDetailIntent, CredentialDetailState, CredentialDetailLabel>

class CredentialDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCredential: GetCredentialUseCase,
    private val deleteCredential: DeleteCredentialUseCase,
) {
    fun create(credentialId: String): CredentialDetailStore =
        object : CredentialDetailStore, Store<CredentialDetailIntent, CredentialDetailState, CredentialDetailLabel> by storeFactory.create(
            name = "CredentialDetailStore",
            initialState = CredentialDetailState(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(credentialId) },
            reducer = ReducerImpl,
        ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<CredentialDetailAction>() {
        override fun invoke() {
            dispatch(CredentialDetailAction.LoadCredential(""))
        }
    }

    private inner class ExecutorImpl(private val credentialId: String) : CoroutineExecutor<CredentialDetailIntent, CredentialDetailAction, CredentialDetailState, CredentialDetailMessage, CredentialDetailLabel>() {
        override fun executeAction(action: CredentialDetailAction) {
            when (action) {
                is CredentialDetailAction.LoadCredential -> loadCredential()
            }
        }

        override fun executeIntent(intent: CredentialDetailIntent) {
            when (intent) {
                CredentialDetailIntent.BackClicked -> publish(CredentialDetailLabel.NavigateBack)
                CredentialDetailIntent.DeleteClicked -> delete()
                CredentialDetailIntent.EditClicked -> state().credential?.id?.let {
                    publish(CredentialDetailLabel.NavigateToEdit(it))
                }
                CredentialDetailIntent.Retry -> {
                    dispatch(CredentialDetailMessage.Loading)
                    loadCredential()
                }
            }
        }

        private fun loadCredential() {
            scope.launch {
                runCatching { getCredential(credentialId) }
                    .onSuccess { credential ->
                        if (credential != null) {
                            dispatch(CredentialDetailMessage.CredentialLoaded(credential))
                        } else {
                            dispatch(CredentialDetailMessage.LoadFailed(AppError("Credential not found")))
                        }
                    }
                    .onFailure { dispatch(CredentialDetailMessage.LoadFailed(AppError(it.message ?: "Unknown error", it))) }
            }
        }

        private fun delete() {
            scope.launch {
                runCatching { deleteCredential(credentialId) }
                    .onSuccess {
                        publish(CredentialDetailLabel.NavigateBack)
                    }
                    .onFailure { dispatch(CredentialDetailMessage.DeleteFailed(AppError(it.message ?: "Unknown error", it))) }
            }
        }
    }

    private object ReducerImpl : Reducer<CredentialDetailState, CredentialDetailMessage> {
        override fun CredentialDetailState.reduce(msg: CredentialDetailMessage): CredentialDetailState =
            when (msg) {
                is CredentialDetailMessage.CredentialLoaded -> copy(
                    credential = msg.credential.toUiModel(),
                    initialLoad = InitialLoad.Loaded,
                )
                is CredentialDetailMessage.LoadFailed -> copy(
                    initialLoad = InitialLoad.Failed(msg.error),
                )
                is CredentialDetailMessage.DeleteFailed -> copy(
                    deleteError = msg.error,
                )
                CredentialDetailMessage.Deleted -> this
                CredentialDetailMessage.Loading -> copy(
                    initialLoad = InitialLoad.Loading,
                )
            }
    }
}
