package com.hivian.model.domain

import com.google.gson.annotations.SerializedName


data class PokemonStat (

    val name: String,
    val baseStat: Int,
    val effort: Int

)