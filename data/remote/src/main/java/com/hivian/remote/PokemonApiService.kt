package com.hivian.remote

import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemon
import com.hivian.model.dto.network.NetworkPokemonObject
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @POST("/api/session")
    suspend fun fetchTopPokemonsAsync(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = 20) : ApiResult<NetworkPokemon>

    @GET("pokemon/{name}")
    suspend fun fetchPokemonDetailAsync(@Path("name") name : String) : NetworkPokemonObject

}