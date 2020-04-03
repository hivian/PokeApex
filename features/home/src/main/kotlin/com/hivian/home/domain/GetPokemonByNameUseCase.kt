package com.hivian.home.domain

import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.ResultWrapper


/**
 * Use case that gets a [ResultWrapper][Pokemon] from [PokedexRepository]
 * and makes some specific logic actions on it.
 *
 */
class GetPokemonByNameUseCase(private val repository: PokedexRepository) {

    suspend operator fun invoke(forceRefresh: Boolean = false, name: String): ResultWrapper<Pokemon> {
        return repository.getPokemonDetailWithCache(forceRefresh, name)
    }
}