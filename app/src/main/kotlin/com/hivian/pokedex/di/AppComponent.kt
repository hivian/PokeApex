package com.hivian.pokedex.di

import com.hivian.home.di.featureHomeModule
import com.hivian.local.di.localModule
import com.hivian.model.di.modelModule
import com.hivian.remote.di.createRemoteModule
import com.hivian.repository.di.repositoryModule
import org.koin.dsl.module
import timber.log.Timber

const val BASE_URL = "https://pokeapi.co/api/v2/"

val timberModule = module {
    single { Timber.DebugTree() }
}

val appComponent = listOf(createRemoteModule(BASE_URL),
    repositoryModule, featureHomeModule,
    localModule, modelModule, timberModule)
