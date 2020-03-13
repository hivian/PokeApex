package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

class NetworkPokemonMoveObject (

    @SerializedName("move")
    val move: NetworkPokemonMove

    //@SerializedName("version_group_details")
    //val isHidden: Boolean,

)