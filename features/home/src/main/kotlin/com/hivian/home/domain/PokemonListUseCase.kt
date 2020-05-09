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

    suspend fun allPokemonApiCall(forceRefresh: Boolean = false, offset: Int = 0, limit: Int = Constants.POKEMON_LIST_SIZE): NetworkWrapper {
        return repository.allPokemonApiCall(forceRefresh, offset, limit)
    }

    fun getAllPokemonFilter(pattern: String): LiveData<List<Pokemon>> =
        repository.getAllPokemonByPatternLocal(pattern)

    fun getAllPokemonFavoritesFilter(): LiveData<List<Pokemon>> =
        repository.getAllPokemonFavoritesByPatternLocal()

    fun getAllPokemonCaughtFilter(): LiveData<List<Pokemon>> =
        repository.getAllPokemonCaughtByPatternLocal()

}