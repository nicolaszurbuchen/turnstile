package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.GetCredentialsUseCase
import io.nicolaszurbuchen.turnstile.feature.vault.domain.usecase.SignOutUseCase
import io.nicolaszurbuchen.turnstile.infra.ui.AppError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface CredentialListStore : Store<CredentialListIntent, CredentialListState, CredentialListLabel>

class CredentialListStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCredentials: GetCredentialsUseCase,
    private val signOut: SignOutUseCase,
) {
    fun create(): CredentialListStore =
        object : CredentialListStore, Store<CredentialListIntent, CredentialListState, CredentialListLabel> by storeFactory.create(
            name = "CredentialListStore",
            initialState = CredentialListState(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<CredentialListAction>() {
        override fun invoke() {
            dispatch(CredentialListAction.ObserveEntries)
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<CredentialListIntent, CredentialListAction, CredentialListState, CredentialListMessage, CredentialListLabel>() {
        private var observationJob: Job? = null

        override fun executeAction(action: CredentialListAction) {
            when (action) {
                CredentialListAction.ObserveEntries -> observeEntries()
            }
        }

        override fun executeIntent(intent: CredentialListIntent) {
            when (intent) {
                is CredentialListIntent.EntryClicked -> publish(CredentialListLabel.NavigateToDetail(intent.id))
                CredentialListIntent.CreateClicked -> publish(CredentialListLabel.NavigateToCreate)
                CredentialListIntent.SignOutClicked -> performSignOut()
                CredentialListIntent.DismissStreamError -> dispatch(CredentialListMessage.DismissStreamError)
                CredentialListIntent.RetryInitialLoad -> {
                    dispatch(CredentialListMessage.ResetToLoading)
                    observeEntries()
                }
            }
        }

        private fun observeEntries() {
            observationJob?.cancel()
            observationJob = getCredentials()
                .onEach { dispatch(CredentialListMessage.EntriesLoaded(it)) }
                .catch { error ->
                    val appError = AppError(error.message ?: "Unknown error", error)
                    if (state().initialLoad !is InitialLoad.Loaded) {
                        dispatch(CredentialListMessage.InitialLoadFailed(appError))
                    } else {
                        dispatch(CredentialListMessage.StreamErrored(appError))
                    }
                }
                .launchIn(scope)
        }

        private fun performSignOut() {
            scope.launch {
                signOut()
                publish(CredentialListLabel.NavigateToAuth)
            }
        }
    }

    private object ReducerImpl : Reducer<CredentialListState, CredentialListMessage> {
        override fun CredentialListState.reduce(msg: CredentialListMessage): CredentialListState =
            when (msg) {
                is CredentialListMessage.EntriesLoaded -> copy(
                    entries = msg.entries.toUiModels(),
                    initialLoad = InitialLoad.Loaded,
                    streamError = null,
                )

                is CredentialListMessage.InitialLoadFailed -> copy(
                    initialLoad = InitialLoad.Failed(msg.error),
                )

                is CredentialListMessage.StreamErrored -> copy(
                    streamError = msg.error,
                )

                CredentialListMessage.DismissStreamError -> copy(
                    streamError = null,
                )

                CredentialListMessage.ResetToLoading -> copy(
                    initialLoad = InitialLoad.Loading,
                )
            }
    }
}
