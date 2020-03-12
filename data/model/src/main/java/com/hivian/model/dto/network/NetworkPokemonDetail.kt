package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkPokemonDetail(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("abilities")
    val abilities: List<NetworkPokemonAbilityResult>,

    @SerializedName("forms")
    val forms: List<NetworkPokemonForm>,

    @SerializedName("sprites")
    val sprites: NetworkPokemonSprites,

    @SerializedName("url")
    var url: String
)