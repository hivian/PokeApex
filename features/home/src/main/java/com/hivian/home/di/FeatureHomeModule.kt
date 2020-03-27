package com.hivian.home.di

import com.hivian.home.domain.GetTopPokemonsUseCase
import com.hivian.home.pokemon_list.PokemonListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val featureHomeModule = module {
    factory { GetTopPokemonsUseCase(get()) }
    viewModel { PokemonListViewModel(get(), get()) }
}