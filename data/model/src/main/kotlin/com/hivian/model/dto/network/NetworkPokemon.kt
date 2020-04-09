package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkPokemon(

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String

)
