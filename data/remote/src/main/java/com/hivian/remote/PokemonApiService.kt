package com.hivian.remote

import com.hivian.model.dto.network.NetworkPokemonDetail
import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemon
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("/pokemon")
    fun fetchTopPokemonsAsync(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = 20) : Deferred<ApiResult<NetworkPokemon>>

    @GET("/pokemon/{name}")
    fun fetchPokemonDetailAsync(@Path("name") name : String) : Deferred<NetworkPokemonDetail>

}