package com.hivian.home.domain

import androidx.lifecycle.LiveData
import com.hivian.common.Constants
import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.NetworkWrapper


/**
 * Use case that gets a [ResultWrapper][List][Pokemon] from [PokedexRepository]
 * and makes some specific logic actions on it.
 *
 */
class PokemonListUseCase(private val repository: PokedexRepository) {

    suspend fun getAllPokemonRemote(forceRefresh: Boolean = false, offset: Int = 0, limit: Int = Constants.POKEMON_LIST_SIZE): NetworkWrapper<List<Pokemon>> {
        return repository.getPokemonListWithCacheRemote(forceRefresh, offset, limit)
    }

    fun getAllPokemonFilter(pattern: String): LiveData<List<Pokemon>> =
        repository.getAllPokemonByPatternLocal(pattern)

    fun getAllPokemonFavoritesFilter(pattern: String): LiveData<List<Pokemon>> =
        repository.getAllPokemonFavoritesByPatternLocal(pattern)

    fun getAllPokemonCaughtFilter(pattern: String): LiveData<List<Pokemon>> =
        repository.getAllPokemonCaughtByPatternLocal(pattern)

}