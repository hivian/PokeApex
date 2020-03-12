package com.hivian.local.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

private const val DATABASE = "DATABASE"

val localModule = module {
    //single(DATABASE) { ArchAppDatabase.buildDatabase(androidContext()) }
    //factory { (get(DATABASE) as ArchAppDatabase).userDao() }
}