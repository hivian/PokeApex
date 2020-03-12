package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

class NetworkPokemonAbilityResult (

    @SerializedName("ability")
    val ability: NetworkPokemonAbility,

    @SerializedName("is_hidden")
    val isHidden: Boolean,

    @SerializedName("slot")
    val slot: Int

)