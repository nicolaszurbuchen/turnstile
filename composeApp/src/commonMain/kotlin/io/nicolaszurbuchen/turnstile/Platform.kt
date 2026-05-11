package io.nicolaszurbuchen.turnstile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform