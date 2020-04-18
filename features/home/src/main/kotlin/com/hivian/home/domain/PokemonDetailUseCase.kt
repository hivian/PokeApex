package com.hivian.home.domain

import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.ResultWrapper


/**
 * Use case that gets a [ResultWrapper][Pokemon] from [PokedexRepository]
 * and makes some specific logic actions on it.
 *
 */
class PokemonDetailUseCase(private val repository: PokedexRepository) {

    suspend fun getDetailWithCache(name: String): ResultWrapper<Pokemon> =
        repository.getPokemonDetailWithCacheRemote(name)

    suspend fun updateFavoriteStatus(id: Int, favorite: Boolean) {
        repository.updateFavoriteStatusLocal(id, favorite)
    }

    suspend fun updateCaughtStatus(id: Int, caught: Boolean) {
        repository.updateCaughtStatusLocal(id, caught)
    }
}