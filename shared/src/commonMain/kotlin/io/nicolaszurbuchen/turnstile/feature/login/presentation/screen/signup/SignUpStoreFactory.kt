package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.signup

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.nicolaszurbuchen.turnstile.feature.login.domain.usecase.SignUpWithEmailUseCase
import io.nicolaszurbuchen.turnstile.feature.login.domain.validation.LoginValidation
import kotlinx.coroutines.launch

interface SignUpStore : Store<SignUpIntent, SignUpState, SignUpLabel>

class SignUpStoreFactory(
    private val storeFactory: StoreFactory,
    private val signUpWithEmail: SignUpWithEmailUseCase,
) {
    fun create(): SignUpStore =
        object :
            SignUpStore,
            Store<SignUpIntent, SignUpState, SignUpLabel> by storeFactory.create(
                name = "SignUpStore",
                initialState = SignUpState(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private inner class ExecutorImpl : CoroutineExecutor<SignUpIntent, SignUpAction, SignUpState, SignUpMessage, SignUpLabel>() {
        override fun executeIntent(intent: SignUpIntent) {
            when (intent) {
                is SignUpIntent.UsernameChanged -> dispatch(SignUpMessage.UsernameChanged(intent.value))
                is SignUpIntent.EmailChanged -> dispatch(SignUpMessage.EmailChanged(intent.value))
                is SignUpIntent.PasswordChanged -> dispatch(SignUpMessage.PasswordChanged(intent.value))
                SignUpIntent.Submit -> register()
                SignUpIntent.SignInClicked -> publish(SignUpLabel.NavigateToSignIn)
            }
        }

        private fun register() {
            val state = state()
            if (!state.canSubmit) return

            val usernameError = LoginValidation.validateUsername(state.username)
            val emailError = LoginValidation.validateEmail(state.email)
            val passwordError = LoginValidation.validatePassword(state.password)

            if (usernameError != null || emailError != null || passwordError != null) {
                dispatch(
                    SignUpMessage.SetErrors(
                        usernameError?.asStringResource(),
                        emailError?.asStringResource(),
                        passwordError?.asStringResource(),
                    ),
                )
                return
            }

            scope.launch {
                dispatch(SignUpMessage.StartedLoading)
                runCatching { signUpWithEmail(state.username, state.email, state.password) }
                    .onSuccess {
                        dispatch(SignUpMessage.RegisterSucceeded)
                        publish(SignUpLabel.NavigateHome)
                    }
                    .onFailure {
                        dispatch(
                            SignUpMessage.RegisterFailed(
                                it.message ?: "Unknown error",
                            ),
                        )
                    }
            }
        }
    }

    private object ReducerImpl : Reducer<SignUpState, SignUpMessage> {
        override fun SignUpState.reduce(msg: SignUpMessage): SignUpState =
            when (msg) {
                is SignUpMessage.UsernameChanged -> {
                    copy(username = msg.value, usernameError = null, submitError = null)
                }

                is SignUpMessage.EmailChanged -> {
                    copy(email = msg.value, emailError = null, submitError = null)
                }

                is SignUpMessage.PasswordChanged -> {
                    copy(password = msg.value, passwordError = null, submitError = null)
                }

                is SignUpMessage.SetErrors -> {
                    copy(
                        usernameError = msg.usernameError,
                        emailError = msg.emailError,
                        passwordError = msg.passwordError,
                    )
                }

                SignUpMessage.StartedLoading -> {
                    copy(loading = true, submitError = null)
                }

                SignUpMessage.RegisterSucceeded -> {
                    copy(loading = false)
                }

                is SignUpMessage.RegisterFailed -> {
                    copy(loading = false, submitError = msg.message)
                }
            }
    }
}
