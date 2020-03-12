package com.hivian.pokedex.di

import com.hivian.local.di.localModule
import com.hivian.remote.di.createRemoteModule
import com.hivian.repository.di.repositoryModule

const val BASE_URL = "https://pokeapi.co/api/v2/"

val appComponent= listOf(createRemoteModule(BASE_URL), repositoryModule, localModule)