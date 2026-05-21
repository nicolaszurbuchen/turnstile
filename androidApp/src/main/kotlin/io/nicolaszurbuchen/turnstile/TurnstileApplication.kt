package io.nicolaszurbuchen.turnstile

import android.app.Application
import io.nicolaszurbuchen.turnstile.infra.di.initKoin
import org.koin.android.ext.koin.androidContext

class TurnstileApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            additionalModules = listOf(platformModule),
            appDeclaration = {
                androidContext(this@TurnstileApplication)
            },
        )
    }
}
