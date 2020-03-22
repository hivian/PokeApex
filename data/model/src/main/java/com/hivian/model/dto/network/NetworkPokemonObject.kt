package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.database.DbPokemonSprites
import com.hivian.model.dto.database.DbPokemonStat
import com.hivian.model.dto.database.DbPokemonType

data class NetworkPokemonObject(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("abilities")
    val abilities: List<NetworkPokemonAbilityObject>,

    @SerializedName("forms")
    val forms: List<NetworkPokemonForm>,

    @SerializedName("moves")
    val moves: List<NetworkPokemonMoveObject>,

    @SerializedName("sprites")
    val sprites: NetworkPokemonSprites,

    @SerializedName("stats")
    val stats: List<NetworkPokemonStatObject>,

    @SerializedName("types")
    val types: List<NetworkPokemonTypeObject>

)

