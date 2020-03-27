package com.hivian.home.di

import com.hivian.home.domain.GetPokemonByNameUseCase
import com.hivian.home.domain.GetTopPokemonsUseCase
import com.hivian.home.pokemon_detail.PokemonDetailViewModel
import com.hivian.home.pokemon_list.PokemonListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val featureHomeModule = module {
    factory { GetTopPokemonsUseCase(get()) }
    factory { GetPokemonByNameUseCase(get()) }
    viewModel { PokemonListViewModel(get(), get()) }
    viewModel { PokemonDetailViewModel(get(), get()) }
}