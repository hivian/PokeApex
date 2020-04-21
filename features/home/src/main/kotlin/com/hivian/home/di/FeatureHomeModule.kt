package com.hivian.home.di

import com.hivian.home.domain.PokemonDetailUseCase
import com.hivian.home.domain.PokemonListUseCase
import com.hivian.home.pokemon_detail.PokemonDetailViewModel
import com.hivian.home.pokemon_list.PokemonListViewModel
import com.hivian.home.preferences.HomeSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    single { HomeSharedPrefs(androidContext()) }
    factory { PokemonListUseCase(get()) }
    factory { PokemonDetailUseCase(get()) }
    viewModel { PokemonListViewModel(get(), get()) }
    viewModel { (name : String) ->PokemonDetailViewModel(name, get(), get()) }
}