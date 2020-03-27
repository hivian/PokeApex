package com.hivian.repository.di

import com.hivian.repository.AppDispatchers
import com.hivian.repository.PokedexRepository
import com.hivian.repository.PokedexRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory<PokedexRepository> { PokedexRepositoryImpl(get(), get(), get()) }
}
