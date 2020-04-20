package com.hivian.home.domain

import androidx.lifecycle.LiveData
import com.hivian.common.Constants
import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.ResultWrapper


/**
 * Use case that gets a [ResultWrapper][List][Pokemon] from [PokedexRepository]
 * and makes some specific logic actions on it.
 *
 */
class PokemonListUseCase(private val repository: PokedexRepository) {

    suspend fun getAllPokemonRemote(forceRefresh: Boolean = false, offset: Int = 0, limit: Int = Constants.POKEMON_LIST_SIZE): ResultWrapper<List<Pokemon>> {
        return repository.getPokemonListWithCacheRemote(forceRefresh, offset, limit)
    }

    suspend fun getPokemonListByPatternFilter(pattern: String): List<Pokemon> =
        repository.getPokemonListByPatternLocal(pattern)

    fun getAllPokemonFilter(): LiveData<List<Pokemon>> =
        repository.getAllPokemonLocal()

    fun getPokemonFavoritesFilter(): LiveData<List<Pokemon>> =
        repository.getPokemonFavoritesLocal()

    fun getPokemonCaughtFilter(): LiveData<List<Pokemon>> =
        repository.getPokemonCaughtLocal()

}