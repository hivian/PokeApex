package com.hivian.pokedex

import android.app.Application
import com.hivian.pokedex.di.appComponent
import org.koin.android.ext.android.startKoin

open class App: Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION
    open fun configureDi() =
        startKoin(this, provideComponent())

    // PUBLIC AP
    open fun provideComponent() = appComponent
}