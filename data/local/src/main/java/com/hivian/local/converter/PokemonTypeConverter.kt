package com.hivian.local.converter

import androidx.room.TypeConverter
import com.hivian.common.generic.fromJson
import com.hivian.common.generic.toJson
import com.hivian.model.dto.database.DbPokemonType

class PokemonTypeConverter {

    @TypeConverter
    fun objectToJson(value: List<DbPokemonType>): String {
        return toJson(value)
    }

    @TypeConverter
    fun jsonToObject(value: String): List<DbPokemonType> {
        return fromJson<List<DbPokemonType>>(value)
    }

}