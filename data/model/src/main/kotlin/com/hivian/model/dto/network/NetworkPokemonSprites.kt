package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkPokemonSprites(
    @SerializedName("back_default")
    val backDefault : String?,
    @SerializedName("back_default_female")
    val backDefaultFemale : String?,
    @SerializedName("back_shiny")
    val backShiny : String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale : String?,
    @SerializedName("front_default")
    val frontDefault : String?,
    @SerializedName("front_default_female")
    val frontDefaultFemale : String?,
    @SerializedName("front_shiny")
    val frontShiny : String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale : String?
)
