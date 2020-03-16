package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

class NetworkPokemonTypeObject(

    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val type: NetworkPokemonType

)
