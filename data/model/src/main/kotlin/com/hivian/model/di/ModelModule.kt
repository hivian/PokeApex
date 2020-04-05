package com.hivian.model.di

import com.hivian.model.mapper.MapperPokedexRepository
import com.hivian.model.mapper.MapperPokemonDbToDomainImpl
import com.hivian.model.mapper.MapperPokemonRemoteToDbImpl
import org.koin.dsl.module

val modelModule = module {
    factory { MapperPokedexRepository(MapperPokemonRemoteToDbImpl(), MapperPokemonDbToDomainImpl()) }
}