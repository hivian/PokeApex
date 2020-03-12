package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName
import com.sun.xml.internal.ws.developer.Serialization

data class NetworkPokemonForm(
    @SerializedName("name")
    val name : String,

    @SerializedName("url")
    val url : String
)