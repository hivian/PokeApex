package com.hivian.local.di

import com.hivian.local.PokedexDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

private const val DATABASE = "DATABASE"

val localModule = module {
    single(DATABASE) { PokedexDatabase.buildDatabase(androidContext()) }
    factory { (get(DATABASE) as PokedexDatabase).pokedexDao() }
}