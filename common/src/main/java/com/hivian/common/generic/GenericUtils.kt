package com.hivian.common.generic

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> toJson(data: T): String {
    return Gson().toJson(data)
}

inline fun <reified T> fromJson(json: String) = Gson().fromJson<T>(json, object: TypeToken<T>() {}.type)
