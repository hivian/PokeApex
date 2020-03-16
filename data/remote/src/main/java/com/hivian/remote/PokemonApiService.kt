package com.hivian.remote

import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemon
import com.hivian.model.dto.network.NetworkPokemonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("/pokemon")
    fun fetchTopPokemonsAsync(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = 20) : Deferred<ApiResult<NetworkPokemonObject>>

    @GET("/pokemon/{name}")
    fun fetchPokemonDetailAsync(@Path("name") name : String) : Deferred<NetworkPokemonObject>

}