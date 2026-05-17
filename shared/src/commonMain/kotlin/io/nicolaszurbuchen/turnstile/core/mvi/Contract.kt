package io.nicolaszurbuchen.turnstile.core.mvi

/** Marker for per-screen state classes. */
interface State

/** Marker for a per-screen trigger hierarchy. */
interface Trigger

/** Marker for a user-originated trigger (sent from the UI). */
interface Intent

/** Marker for a system-originated trigger (dispatched from internal command results). */
interface Action

/** Marker for an async work description, returned from the reducer and executed by the ViewModel. */
interface Command

/** Marker for a one-shot UI event emitted out of the ViewModel. */
interface Event
