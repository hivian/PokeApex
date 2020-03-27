package com.hivian.pokedex

import android.app.Application
import com.hivian.pokedex.di.toto
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
        configureTimber()
    }

    // CONFIGURATION
    private fun configureDi() =
        startKoin(this, provideComponent())

    private fun configureTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(get<Timber.DebugTree>())
    }

    // PUBLIC API
    private fun provideComponent() = toto
}
