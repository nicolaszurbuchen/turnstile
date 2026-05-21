package io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard

import io.nicolaszurbuchen.turnstile.infra.mvi.Next
import io.nicolaszurbuchen.turnstile.infra.mvi.Reducer
import io.nicolaszurbuchen.turnstile.infra.ui.Loadable
import io.nicolaszurbuchen.turnstile.infra.ui.onSuccess

object DashboardReducer : Reducer<Loadable<DashboardState>, DashboardTrigger, DashboardCommand, DashboardEvent> {
    override fun reduce(
        state: Loadable<DashboardState>,
        trigger: DashboardTrigger,
    ): Next<Loadable<DashboardState>, DashboardCommand, DashboardEvent> =
        when (trigger) {
            // ── Initial load ─────────────────────────────────────────────────────

            DashboardIntent.Retry -> {
                Next(
                    state = Loadable.Loading,
                    commands = listOf(DashboardCommand.LoadEntries),
                )
            }

            is DashboardAction.EntriesLoaded -> {
                Next(
                    state = Loadable.Success(trigger.entries.toDashboardState()),
                )
            }

            is DashboardAction.LoadFailed -> {
                Next(
                    state = Loadable.Failure(trigger.error),
                )
            }

            // ── Refresh ───────────────────────────────────────────────────────────

            DashboardIntent.Refresh -> {
                state.onSuccess { content ->
                    Next(
                        state = Loadable.Success(content.copy(refreshing = true, refreshError = null)),
                        commands = listOf(DashboardCommand.Refresh),
                    )
                }
            }

            is DashboardAction.RefreshSucceeded -> {
                state.onSuccess {
                    Next(
                        state = Loadable.Success(trigger.entries.toDashboardState()),
                    )
                }
            }

            is DashboardAction.RefreshFailed -> {
                state.onSuccess { content ->
                    Next(
                        state = Loadable.Success(content.copy(refreshing = false, refreshError = trigger.error)),
                    )
                }
            }

            DashboardIntent.DismissRefreshError -> {
                state.onSuccess { content ->
                    Next(
                        state = Loadable.Success(content.copy(refreshError = null)),
                    )
                }
            }

            // ── Entry actions ─────────────────────────────────────────────────────

            is DashboardIntent.EntryClicked -> {
                state.onSuccess {
                    Next(
                        state = state,
                        events = listOf(DashboardEvent.NavigateToDetail(trigger.id)),
                    )
                }
            }

            is DashboardIntent.DeleteEntry -> {
                state.onSuccess {
                    Next(
                        state = state,
                        commands = listOf(DashboardCommand.DeleteEntry(trigger.id)),
                    )
                }
            }

            is DashboardAction.EntryDeleted -> {
                state.onSuccess { content ->
                    Next(
                        state =
                            Loadable.Success(
                                content.copy(entries = content.entries.filter { it.id != trigger.id }),
                            ),
                    )
                }
            }

            is DashboardAction.DeleteFailed -> {
                state.onSuccess { content ->
                    Next(
                        state = Loadable.Success(content),
                        events = listOf(DashboardEvent.ShowDeleteError(trigger.error)),
                    )
                }
            }
        }
}
