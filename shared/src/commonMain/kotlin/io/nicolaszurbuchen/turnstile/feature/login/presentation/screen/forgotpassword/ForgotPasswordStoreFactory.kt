package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SendPasswordResetEmailUseCase
import kotlinx.coroutines.launch

interface ForgotPasswordStore : Store<ForgotPasswordIntent, ForgotPasswordState, ForgotPasswordLabel>

class ForgotPasswordStoreFactory(
    private val storeFactory: StoreFactory,
    private val sendPasswordResetEmail: SendPasswordResetEmailUseCase,
) {
    fun create(): ForgotPasswordStore =
        object : ForgotPasswordStore, Store<ForgotPasswordIntent, ForgotPasswordState, ForgotPasswordLabel> by storeFactory.create(
            name = "ForgotPasswordStore",
            initialState = ForgotPasswordState(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<ForgotPasswordIntent, ForgotPasswordAction, ForgotPasswordState, ForgotPasswordMessage, ForgotPasswordLabel>() {
        override fun executeIntent(intent: ForgotPasswordIntent) {
            when (intent) {
                is ForgotPasswordIntent.EmailChanged -> dispatch(ForgotPasswordMessage.EmailChanged(intent.value, validateEmail(intent.value)))
                ForgotPasswordIntent.Submit -> sendResetEmail()
                ForgotPasswordIntent.BackClicked -> publish(ForgotPasswordLabel.NavigateBack)
            }
        }

        private fun sendResetEmail() {
            val state = state()
            if (!state.canSubmit) return

            scope.launch {
                dispatch(ForgotPasswordMessage.StartedLoading)
                runCatching { sendPasswordResetEmail(state.email) }
                    .onSuccess {
                        dispatch(ForgotPasswordMessage.ResetEmailSent)
                    }
                    .onFailure { dispatch(ForgotPasswordMessage.ResetFailed(it.message ?: "Unknown error")) }
            }
        }
    }

    private object ReducerImpl : Reducer<ForgotPasswordState, ForgotPasswordMessage> {
        override fun ForgotPasswordState.reduce(msg: ForgotPasswordMessage): ForgotPasswordState =
            when (msg) {
                is ForgotPasswordMessage.EmailChanged -> copy(email = msg.value, emailError = msg.error, submitError = null)
                ForgotPasswordMessage.StartedLoading -> copy(loading = true, submitError = null)
                ForgotPasswordMessage.ResetEmailSent -> copy(loading = false, submitted = true)
                is ForgotPasswordMessage.ResetFailed -> copy(loading = false, submitError = msg.message)
            }
    }
}
