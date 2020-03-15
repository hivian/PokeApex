package com.hivian.model.dto.database

import com.google.gson.annotations.SerializedName


data class DbPokemonStat (

    val name: String,
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int

)