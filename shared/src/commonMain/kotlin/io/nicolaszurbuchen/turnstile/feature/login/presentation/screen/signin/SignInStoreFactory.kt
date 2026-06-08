package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signin

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignInWithEmailUseCase
import kotlinx.coroutines.launch

interface SignInStore : Store<SignInIntent, SignInState, SignInLabel>

class SignInStoreFactory(
    private val storeFactory: StoreFactory,
    private val signInWithEmail: SignInWithEmailUseCase,
) {
    fun create(): SignInStore =
        object : SignInStore, Store<SignInIntent, SignInState, SignInLabel> by storeFactory.create(
            name = "SignInStore",
            initialState = SignInState(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<SignInIntent, SignInAction, SignInState, SignInMessage, SignInLabel>() {
        override fun executeIntent(intent: SignInIntent) {
            when (intent) {
                is SignInIntent.EmailChanged -> dispatch(SignInMessage.EmailChanged(intent.value, validateEmail(intent.value)))
                is SignInIntent.PasswordChanged -> dispatch(SignInMessage.PasswordChanged(intent.value, validatePassword(intent.value)))
                SignInIntent.Submit -> login()
                SignInIntent.SignUpClicked -> publish(SignInLabel.NavigateToSignUp)
                SignInIntent.ForgotPasswordClicked -> publish(SignInLabel.NavigateToForgotPassword)
            }
        }

        private fun login() {
            val state = state()
            if (!state.canSubmit) return

            scope.launch {
                dispatch(SignInMessage.StartedLoading)
                runCatching { signInWithEmail(state.email, state.password) }
                    .onSuccess {
                        dispatch(SignInMessage.LoginSucceeded)
                        publish(SignInLabel.NavigateHome)
                    }
                    .onFailure { dispatch(SignInMessage.LoginFailed(it.message ?: "Unknown error")) }
            }
        }
    }

    private object ReducerImpl : Reducer<SignInState, SignInMessage> {
        override fun SignInState.reduce(msg: SignInMessage): SignInState =
            when (msg) {
                is SignInMessage.EmailChanged -> copy(email = msg.value, emailError = msg.error, submitError = null)
                is SignInMessage.PasswordChanged -> copy(password = msg.value, passwordError = msg.error, submitError = null)
                SignInMessage.StartedLoading -> copy(loading = true, submitError = null)
                SignInMessage.LoginSucceeded -> copy(loading = false)
                is SignInMessage.LoginFailed -> copy(loading = false, submitError = msg.message)
            }
    }
}
