package com.hivian.local.converter

import androidx.room.TypeConverter
import com.hivian.common.generic.fromJson
import com.hivian.common.generic.toJson

class StringListConverter {

    @TypeConverter
    fun listToJson(value: List<String>): String {
        return toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        return fromJson<List<String>>(value)
    }
}
