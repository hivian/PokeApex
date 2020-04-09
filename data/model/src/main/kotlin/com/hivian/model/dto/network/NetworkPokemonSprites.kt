package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkPokemonSprites(
    @SerializedName("back_default")
    val backDefault : String? = null,
    @SerializedName("back_default_female")
    val backDefaultFemale : String? = null,
    @SerializedName("back_shiny")
    val backShiny : String? = null,
    @SerializedName("back_shiny_female")
    val backShinyFemale : String? = null,
    @SerializedName("front_default")
    val frontDefault : String? = null,
    @SerializedName("front_default_female")
    val frontDefaultFemale : String? = null,
    @SerializedName("front_shiny")
    val frontShiny : String? = null,
    @SerializedName("front_shiny_female")
    val frontShinyFemale : String? = null
)
