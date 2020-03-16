package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.database.DbPokemonSprites
import com.hivian.model.dto.database.DbPokemonStat
import com.hivian.model.dto.database.DbPokemonType

data class ApiResult<T>(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    var next: String,

    @SerializedName("previous")
    var previous: String,

    @SerializedName("results")
    val results: List<T>
)
