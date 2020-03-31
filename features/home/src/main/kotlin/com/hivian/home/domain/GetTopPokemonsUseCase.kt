package com.hivian.home.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.Resource


/**
 * Use case that gets a [Resource][List][Pokemon] from [PokedexRepository]
 * and makes some specific logic actions on it.
 *
 */
class GetTopPokemonsUseCase(private val repository: PokedexRepository) {

    suspend operator fun invoke(forceRefresh: Boolean = false): LiveData<Resource<List<Pokemon>>> {
        return Transformations.map(repository.getTopPokemonsWithCache(forceRefresh)) {
            it // Place here your specific logic actions
        }
    }
}