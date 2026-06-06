package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.forgotpassword

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class ForgotPasswordViewModel(
    factory: ForgotPasswordStoreFactory,
) : ViewModel() {
    private val store = factory.create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ForgotPasswordState> = store.stateFlow

    val labels: Flow<ForgotPasswordLabel> = store.labels

    fun onIntent(intent: ForgotPasswordIntent) {
        store.accept(intent)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}
