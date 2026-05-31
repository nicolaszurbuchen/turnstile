package io.nicolaszurbuchen.turnstile

import androidx.compose.ui.window.ComposeUIViewController
import io.nicolaszurbuchen.turnstile.infra.App

@Suppress("ktlint:standard:function-naming")
fun MainViewController() = ComposeUIViewController { App() }
