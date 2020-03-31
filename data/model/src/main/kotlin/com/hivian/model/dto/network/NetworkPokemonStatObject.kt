package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

class NetworkPokemonStatObject(

    @SerializedName("base_stat")
    val baseStat: Int,

    @SerializedName("effort")
    val effort: Int,

    @SerializedName("stat")
    val stat: NetworkPokemonStat

)
