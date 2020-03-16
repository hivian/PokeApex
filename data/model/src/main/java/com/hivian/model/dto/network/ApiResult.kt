package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName

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
