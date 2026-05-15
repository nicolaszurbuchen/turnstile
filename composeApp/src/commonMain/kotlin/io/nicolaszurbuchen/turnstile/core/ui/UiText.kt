package io.nicolaszurbuchen.turnstile.core.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    /** Hardcoded string — from backend, dynamic values, already-localized content. */
    data class Raw(val value: String) : UiText

    /** Local string resource — compile-time safe, localized by the system. */
    data class Resource(
        val id: StringResource,
        val args: List<Any> = emptyList(),
    ) : UiText

    /** Composite — concatenated UiText values for mixed dynamic/static strings. */
    data class Composite(val parts: List<UiText>) : UiText
}

@Composable
fun UiText.asString(): String = when (this) {
    is UiText.Raw -> value
    is UiText.Resource -> if (args.isEmpty()) {
        stringResource(id)
    } else {
        stringResource(id, *args.toTypedArray())
    }
    is UiText.Composite -> {
        var result = ""
        for (part in parts) {
            result += part.asString()
        }
        result
    }
}