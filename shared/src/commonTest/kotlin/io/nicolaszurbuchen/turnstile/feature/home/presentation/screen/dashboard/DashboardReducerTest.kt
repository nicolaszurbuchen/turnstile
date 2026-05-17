package io.nicolaszurbuchen.turnstile.feature.home.presentation.screen.dashboard

import io.nicolaszurbuchen.turnstile.core.ui.AppError
import io.nicolaszurbuchen.turnstile.core.ui.Loadable
import io.nicolaszurbuchen.turnstile.feature.home.domain.model.PasswordEntry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DashboardReducerTest {
    private val reducer = DashboardReducer

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun fakeEntry(id: String) = PasswordEntry(id, "Name $id", "user$id@test.com", "pw$id")

    private fun fakeEntryUi(id: String) = PasswordEntryUiModel(id, "Name $id", "user$id@test.com")

    private fun fakeError(msg: String = "Test error") = AppError(msg)

    private fun loadedState(
        entries: List<PasswordEntryUiModel> = listOf(fakeEntryUi("1")),
        refreshing: Boolean = false,
        refreshError: AppError? = null,
    ) = Loadable.Success(DashboardState(entries, refreshing, refreshError))

    private fun assertSuccess(state: Loadable<DashboardState>): DashboardState {
        assertIs<Loadable.Success<DashboardState>>(state)
        return (state as Loadable.Success<DashboardState>).data
    }

    // ── onSuccess guard — Loading ─────────────────────────────────────────────

    @Test
    fun `triggers that require loaded state are ignored when state is Loading`() {
        val result =
            reducer.reduce(
                state = Loadable.Loading,
                trigger = DashboardIntent.Refresh,
            )
        assertIs<Loadable.Loading>(result.state)
        assertTrue(result.commands.isEmpty())
        assertTrue(result.events.isEmpty())
    }

    // ── onSuccess guard — Failure ─────────────────────────────────────────────

    @Test
    fun `triggers that require loaded state are ignored when state is Failure`() {
        val result =
            reducer.reduce(
                state = Loadable.Failure(fakeError()),
                trigger = DashboardIntent.Refresh,
            )
        assertIs<Loadable.Failure>(result.state)
        assertTrue(result.commands.isEmpty())
        assertTrue(result.events.isEmpty())
    }

    // ── Retry / initial load ──────────────────────────────────────────────────

    @Test
    fun `Retry transitions to Loading and emits LoadEntries command`() {
        val result = reducer.reduce(Loadable.Failure(fakeError()), DashboardIntent.Retry)
        assertIs<Loadable.Loading>(result.state)
        assertEquals(listOf(DashboardCommand.LoadEntries), result.commands)
    }

    @Test
    fun `Retry from Loading also emits LoadEntries command`() {
        val result = reducer.reduce(Loadable.Loading, DashboardIntent.Retry)
        assertIs<Loadable.Loading>(result.state)
        assertEquals(listOf(DashboardCommand.LoadEntries), result.commands)
    }

    @Test
    fun `EntriesLoaded transitions to Success with mapped entries`() {
        val entries = listOf(fakeEntry("1"), fakeEntry("2"))
        val result = reducer.reduce(Loadable.Loading, DashboardAction.EntriesLoaded(entries))
        val data = assertSuccess(result.state)
        assertEquals(2, data.entries.size)
        assertEquals("1", data.entries[0].id)
    }

    @Test
    fun `LoadFailed transitions to Failure with the given error`() {
        val error = fakeError("Network error")
        val result = reducer.reduce(Loadable.Loading, DashboardAction.LoadFailed(error))
        val failure = result.state
        assertIs<Loadable.Failure>(failure)
        assertEquals("Network error", (failure as Loadable.Failure).error.message)
    }

    // ── Refresh ───────────────────────────────────────────────────────────────

    @Test
    fun `Refresh on Success sets refreshing=true and emits Refresh command`() {
        val result = reducer.reduce(loadedState(), DashboardIntent.Refresh)
        val data = assertSuccess(result.state)
        assertTrue(data.refreshing)
        assertNull(data.refreshError)
        assertEquals(listOf(DashboardCommand.Refresh), result.commands)
    }

    @Test
    fun `Refresh on Success clears existing refreshError`() {
        val state = loadedState(refreshError = fakeError("old"))
        val result = reducer.reduce(state, DashboardIntent.Refresh)
        assertNull(assertSuccess(result.state).refreshError)
    }

    @Test
    fun `RefreshSucceeded replaces entries and clears refreshing flag`() {
        val state = loadedState(refreshing = true)
        val newEntries = listOf(fakeEntry("99"))
        val result = reducer.reduce(state, DashboardAction.RefreshSucceeded(newEntries))
        val data = assertSuccess(result.state)
        assertEquals(1, data.entries.size)
        assertEquals("99", data.entries[0].id)
        assertTrue(!data.refreshing)
    }

    @Test
    fun `RefreshFailed sets refreshError and clears refreshing flag`() {
        val state = loadedState(refreshing = true)
        val error = fakeError("Refresh failed")
        val result = reducer.reduce(state, DashboardAction.RefreshFailed(error))
        val data = assertSuccess(result.state)
        assertTrue(!data.refreshing)
        assertEquals("Refresh failed", data.refreshError?.message)
    }

    @Test
    fun `DismissRefreshError clears refreshError`() {
        val state = loadedState(refreshError = fakeError("error"))
        val result = reducer.reduce(state, DashboardIntent.DismissRefreshError)
        assertNull(assertSuccess(result.state).refreshError)
    }

    // ── Entry interactions ────────────────────────────────────────────────────

    @Test
    fun `EntryClicked emits NavigateToDetail event`() {
        val result = reducer.reduce(loadedState(), DashboardIntent.EntryClicked("1"))
        assertTrue(result.commands.isEmpty())
        assertEquals(listOf(DashboardEvent.NavigateToDetail("1")), result.events)
    }

    @Test
    fun `DeleteEntry emits DeleteEntry command`() {
        val result = reducer.reduce(loadedState(), DashboardIntent.DeleteEntry("1"))
        assertEquals(listOf(DashboardCommand.DeleteEntry("1")), result.commands)
    }

    @Test
    fun `EntryDeleted removes the entry from the list`() {
        val state = loadedState(entries = listOf(fakeEntryUi("1"), fakeEntryUi("2")))
        val result = reducer.reduce(state, DashboardAction.EntryDeleted("1"))
        val data = assertSuccess(result.state)
        assertEquals(1, data.entries.size)
        assertEquals("2", data.entries[0].id)
    }

    @Test
    fun `EntryDeleted with unknown id leaves the list unchanged`() {
        val state = loadedState(entries = listOf(fakeEntryUi("1")))
        val result = reducer.reduce(state, DashboardAction.EntryDeleted("99"))
        assertEquals(1, assertSuccess(result.state).entries.size)
    }

    @Test
    fun `DeleteFailed emits ShowDeleteError event without changing state`() {
        val state = loadedState()
        val error = fakeError("Delete failed")
        val result = reducer.reduce(state, DashboardAction.DeleteFailed(error))
        assertEquals(state, result.state)
        assertEquals(listOf(DashboardEvent.ShowDeleteError(error)), result.events)
    }
}
