package io.nicolaszurbuchen.turnstile.infra.ui

sealed interface InitialLoad {
    data object Loading : InitialLoad

    data object Loaded : InitialLoad

    data class Failed(
        val error: AppError,
    ) : InitialLoad
}
