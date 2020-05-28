package com.hivian.pokedex

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.hivian.pokedex.di.appComponent
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
        configureTimber()
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    // CONFIGURATION
    private fun configureDi() = startKoin {
        androidContext(this@App)
        modules(provideComponent())
    }

    private fun configureTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(get<Timber.DebugTree>())
    }

    // PUBLIC API
    private fun provideComponent() = appComponent
}
