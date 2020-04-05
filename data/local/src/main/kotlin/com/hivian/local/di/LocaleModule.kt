package com.hivian.local.di

import com.hivian.local.PokedexDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATABASE = "DATABASE"

val localModule = module {
    single(named(DATABASE)) { PokedexDatabase.buildDatabase(androidContext()) }
    factory { (get(named(DATABASE)) as PokedexDatabase).pokedexDao() }
}
