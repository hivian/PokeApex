package com.hivian.pokedex

import android.app.Application
import com.hivian.pokedex.di.appComponent
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.android.get
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
        val tree: Timber.DebugTree = get()
        Timber.plant(tree)
    }

    // PUBLIC API
    private fun provideComponent() = appComponent
}