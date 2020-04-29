package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkPokemonObject(

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("height")
    var height: Int = 0,

    @SerializedName("weight")
    var weight: Int = 0,

    @SerializedName("abilities")
    var abilities: List<NetworkPokemonAbilityObject> = emptyList(),

    @SerializedName("forms")
    var forms: List<NetworkPokemonForm> = emptyList(),

    @SerializedName("moves")
    var moves: List<NetworkPokemonMoveObject> = emptyList(),

    @SerializedName("sprites")
    var sprites: NetworkPokemonSprites = NetworkPokemonSprites(),

    @SerializedName("stats")
    var stats: List<NetworkPokemonStatObject> = emptyList(),

    @SerializedName("types")
    var types: List<NetworkPokemonTypeObject> = emptyList()

)

