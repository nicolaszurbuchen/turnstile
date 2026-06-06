package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignUpWithEmailUseCase
import kotlinx.coroutines.launch

interface SignUpStore : Store<SignUpIntent, SignUpState, SignUpLabel>

class SignUpStoreFactory(
    private val storeFactory: StoreFactory,
    private val signUpWithEmail: SignUpWithEmailUseCase,
) {
    fun create(): SignUpStore =
        object : SignUpStore, Store<SignUpIntent, SignUpState, SignUpLabel> by storeFactory.create(
            name = "SignUpStore",
            initialState = SignUpState(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<SignUpIntent, SignUpAction, SignUpState, SignUpMessage, SignUpLabel>() {
        override fun executeIntent(intent: SignUpIntent) {
            when (intent) {
                is SignUpIntent.UsernameChanged -> dispatch(SignUpMessage.UsernameChanged(intent.value, validateUsername(intent.value)))
                is SignUpIntent.EmailChanged -> dispatch(SignUpMessage.EmailChanged(intent.value, validateEmail(intent.value)))
                is SignUpIntent.PasswordChanged -> dispatch(SignUpMessage.PasswordChanged(intent.value, validatePassword(intent.value)))
                SignUpIntent.Submit -> register()
                SignUpIntent.SignInClicked -> publish(SignUpLabel.NavigateToSignIn)
            }
        }

        private fun register() {
            val state = state()
            if (!state.canSubmit) return

            scope.launch {
                dispatch(SignUpMessage.StartedLoading)
                runCatching { signUpWithEmail(state.username, state.email, state.password) }
                    .onSuccess {
                        dispatch(SignUpMessage.RegisterSucceeded)
                        publish(SignUpLabel.NavigateHome)
                    }
                    .onFailure { dispatch(SignUpMessage.RegisterFailed(it.message ?: "Unknown error")) }
            }
        }
    }

    private object ReducerImpl : Reducer<SignUpState, SignUpMessage> {
        override fun SignUpState.reduce(msg: SignUpMessage): SignUpState =
            when (msg) {
                is SignUpMessage.UsernameChanged -> copy(username = msg.value, usernameError = msg.error, submitError = null)
                is SignUpMessage.EmailChanged -> copy(email = msg.value, emailError = msg.error, submitError = null)
                is SignUpMessage.PasswordChanged -> copy(password = msg.value, passwordError = msg.error, submitError = null)
                SignUpMessage.StartedLoading -> copy(loading = true, submitError = null)
                SignUpMessage.RegisterSucceeded -> copy(loading = false)
                is SignUpMessage.RegisterFailed -> copy(loading = false, submitError = msg.message)
            }
    }
}
