package com.hivian.remote

import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemon
import com.hivian.model.dto.network.NetworkPokemonObject
import kotlinx.coroutines.Deferred

class PokemonDatasource(private val pokemonApiService: PokemonApiService) {

    fun fetchTopPokemonsAsync(offset: Int = 0, limit: Int = 20): Deferred<ApiResult<NetworkPokemonObject>>
            = pokemonApiService.fetchTopPokemonsAsync(offset, limit)

    fun fetchPokemonDetailAsync(name: String): Deferred<NetworkPokemonObject>
            = pokemonApiService.fetchPokemonDetailAsync(name)

}