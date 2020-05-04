package com.hivian.remote

import com.hivian.common.Constants
import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemon
import com.hivian.model.dto.network.NetworkPokemonObject

class PokemonDatasource(private val pokemonApiService: PokemonApiService) {

    suspend fun fetchAllPokemonAsync(offset: Int = 0, limit: Int = Constants.POKEMON_LIST_SIZE): ApiResult<NetworkPokemon>
            = pokemonApiService.fetchTopPokemonsAsync(offset, limit)

    suspend fun fetchPokemonDetailAsync(name: String): NetworkPokemonObject
            = pokemonApiService.fetchPokemonDetailAsync(name)

}