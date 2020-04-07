package com.hivian.home.domain

import com.hivian.common.Constants
import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.ResultWrapper


/**
 * Use case that gets a [ResultWrapper][List][Pokemon] from [PokedexRepository]
 * and makes some specific logic actions on it.
 *
 */
class GetTopPokemonsUseCase(private val repository: PokedexRepository) {

    suspend operator fun invoke(forceRefresh: Boolean = false, offset: Int = 0, limit: Int = Constants.POKEMON_LIST_SIZE): ResultWrapper<List<Pokemon>> {
        return repository.getTopPokemonsWithCache(forceRefresh, offset, limit)
    }
}