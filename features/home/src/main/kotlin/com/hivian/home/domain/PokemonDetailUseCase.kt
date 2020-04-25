package com.hivian.home.domain

import androidx.lifecycle.LiveData
import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.NetworkWrapper


/**
 * Use case that gets a [ResultWrapper][Pokemon] from [PokedexRepository]
 * and makes some specific logic actions on it.
 *
 */
class PokemonDetailUseCase(private val repository: PokedexRepository) {

    suspend fun pokemonDetailApiCall(name: String): NetworkWrapper =
        repository.pokemonDetailApiCall(name)

    fun getPokemonDetailLive(name: String): LiveData<Pokemon> =
        repository.getPokemonDetailLocalLive(name)

    suspend fun updateFavoriteStatus(id: Int, favorite: Boolean) {
        repository.updateFavoriteStatusLocal(id, favorite)
    }

    suspend fun updateCaughtStatus(id: Int, caught: Boolean) {
        repository.updateCaughtStatusLocal(id, caught)
    }
}