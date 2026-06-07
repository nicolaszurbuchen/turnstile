package io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.splash.domain.model.SessionStatus
import io.nicolaszurbuchen.turnstile.feature.splash.domain.usecase.ResolveSessionUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface SplashStore : Store<SplashIntent, SplashState, SplashLabel>

class SplashStoreFactory(
    private val storeFactory: StoreFactory,
    private val resolveSession: ResolveSessionUseCase,
) {
    fun create(): SplashStore =
        object : SplashStore, Store<SplashIntent, SplashState, SplashLabel> by storeFactory.create(
            name = "SplashStore",
            initialState = SplashState(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private class BootstrapperImpl : CoroutineBootstrapper<SplashAction>() {
        override fun invoke() {
            scope.launch {
                delay(0) // delay allows subscribers to subscribe
                dispatch(SplashAction.CheckSession)
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<SplashIntent, SplashAction, SplashState, SplashMessage, SplashLabel>() {
        override fun executeAction(action: SplashAction) {
            when (action) {
                SplashAction.CheckSession -> checkSession()
            }
        }

        private fun checkSession() {
            scope.launch {
                val status = resolveSession()
                dispatch(SplashMessage.SessionResolved(status))
                when (status) {
                    SessionStatus.Authenticated -> publish(SplashLabel.NavigateToVault)
                    SessionStatus.Unauthenticated -> publish(SplashLabel.NavigateToAuth)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<SplashState, SplashMessage> {
        override fun SplashState.reduce(msg: SplashMessage): SplashState =
            when (msg) {
                is SplashMessage.SessionResolved -> copy(status = msg.status)
            }
    }
}
