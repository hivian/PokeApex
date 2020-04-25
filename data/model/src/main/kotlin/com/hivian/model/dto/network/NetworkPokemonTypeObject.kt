package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

class NetworkPokemonTypeObject(

    @SerializedName("slot")
    val slot: Int,

    @SerializedName("is_hidden")
    val isHidden: Boolean,

    @SerializedName("type")
    val type: NetworkPokemonType

)
