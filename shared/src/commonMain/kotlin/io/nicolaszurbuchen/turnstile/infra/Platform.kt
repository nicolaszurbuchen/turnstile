package io.nicolaszurbuchen.turnstile.infra

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
