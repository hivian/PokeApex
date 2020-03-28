package com.hivian.remote

import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemon
import com.hivian.model.dto.network.NetworkPokemonObject

class PokemonDatasource(private val pokemonApiService: PokemonApiService) {

    suspend fun fetchTopPokemonsAsync(offset: Int, limit: Int): ApiResult<NetworkPokemon>
            = pokemonApiService.fetchTopPokemonsAsync(offset, limit)

    suspend fun fetchPokemonDetailAsync(name: String): NetworkPokemonObject
            = pokemonApiService.fetchPokemonDetailAsync(name)

}