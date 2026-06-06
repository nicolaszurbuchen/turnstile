package io.nicolaszurbuchen.turnstile.feature.splash.presentation.screen

import androidx.lifecycle.ViewModel
import io.nicolaszurbuchen.turnstile.feature.splash.domain.usecase.ResolveSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SplashViewModel(
    private val resolveSession: ResolveSessionUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    init {
        _state.update { it.copy(status = resolveSession()) }
    }
}
