package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.SaveCredentialUseCase
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import kotlinx.coroutines.launch

interface CredentialEditorStore : Store<CredentialEditorIntent, CredentialEditorState, CredentialEditorLabel>

class CredentialEditorStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCredential: GetCredentialUseCase,
    private val saveCredential: SaveCredentialUseCase,
) {
    fun create(credentialId: String?): CredentialEditorStore =
        object : CredentialEditorStore, Store<CredentialEditorIntent, CredentialEditorState, CredentialEditorLabel> by storeFactory.create(
            name = "CredentialEditorStore",
            initialState = if (credentialId != null) {
                CredentialEditorState(initialLoad = InitialLoad.Loading)
            } else {
                CredentialEditorState()
            },
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(credentialId) },
            reducer = ReducerImpl,
        ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<CredentialEditorAction>() {
        override fun invoke() {
            dispatch(CredentialEditorAction.LoadCredential(""))
        }
    }

    private inner class ExecutorImpl(private val credentialId: String?) : CoroutineExecutor<CredentialEditorIntent, CredentialEditorAction, CredentialEditorState, CredentialEditorMessage, CredentialEditorLabel>() {
        override fun executeAction(action: CredentialEditorAction) {
            when (action) {
                is CredentialEditorAction.LoadCredential -> loadCredential()
            }
        }

        override fun executeIntent(intent: CredentialEditorIntent) {
            when (intent) {
                is CredentialEditorIntent.MemoChanged -> dispatch(CredentialEditorMessage.CredentialLoaded(state().toDomain().copy(memo = intent.value)))
                is CredentialEditorIntent.PasswordChanged -> dispatch(CredentialEditorMessage.CredentialLoaded(state().toDomain().copy(password = intent.value)))
                is CredentialEditorIntent.TitleChanged -> dispatch(CredentialEditorMessage.CredentialLoaded(state().toDomain().copy(title = intent.value)))
                is CredentialEditorIntent.UsernameChanged -> dispatch(CredentialEditorMessage.CredentialLoaded(state().toDomain().copy(username = intent.value)))
                CredentialEditorIntent.CancelClicked -> publish(CredentialEditorLabel.NavigateBack)
                CredentialEditorIntent.SaveClicked -> save()
                CredentialEditorIntent.Retry -> {
                    dispatch(CredentialEditorMessage.ResetToLoading)
                    loadCredential()
                }
                CredentialEditorIntent.DismissSaveError -> dispatch(CredentialEditorMessage.DismissSaveError)
            }
        }

        private fun loadCredential() {
            if (credentialId == null) return
            scope.launch {
                runCatching { getCredential(credentialId) }
                    .onSuccess { credential ->
                        if (credential != null) {
                            dispatch(CredentialEditorMessage.CredentialLoaded(credential))
                        } else {
                            dispatch(CredentialEditorMessage.InitialLoadFailed(AppError("Credential not found")))
                        }
                    }
                    .onFailure { dispatch(CredentialEditorMessage.InitialLoadFailed(AppError(it.message ?: "Unknown error", it))) }
            }
        }

        private fun save() {
            scope.launch {
                dispatch(CredentialEditorMessage.Saving)
                runCatching { saveCredential(state().toDomain()) }
                    .onSuccess {
                        publish(CredentialEditorLabel.NavigateBack)
                    }
                    .onFailure { dispatch(CredentialEditorMessage.SaveFailed(AppError(it.message ?: "Unknown error", it))) }
            }
        }
    }

    private object ReducerImpl : Reducer<CredentialEditorState, CredentialEditorMessage> {
        override fun CredentialEditorState.reduce(msg: CredentialEditorMessage): CredentialEditorState =
            when (msg) {
                is CredentialEditorMessage.CredentialLoaded -> copy(
                    id = msg.credential.id,
                    title = msg.credential.title,
                    username = msg.credential.username,
                    password = msg.credential.password,
                    memo = msg.credential.memo,
                    initialLoad = InitialLoad.Loaded,
                )
                is CredentialEditorMessage.InitialLoadFailed -> copy(
                    initialLoad = InitialLoad.Failed(msg.error),
                )
                CredentialEditorMessage.Saving -> copy(
                    isSaving = true,
                    saveError = null,
                )
                CredentialEditorMessage.Saved -> copy(
                    isSaving = false,
                )
                is CredentialEditorMessage.SaveFailed -> copy(
                    isSaving = false,
                    saveError = msg.error,
                )
                CredentialEditorMessage.ResetToLoading -> copy(
                    initialLoad = InitialLoad.Loading,
                )
                CredentialEditorMessage.DismissSaveError -> copy(
                    saveError = null,
                )
            }
    }
}
