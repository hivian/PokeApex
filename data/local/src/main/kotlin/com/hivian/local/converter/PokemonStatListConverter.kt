package com.hivian.local.converter

import androidx.room.TypeConverter
import com.hivian.common.generic.fromJson
import com.hivian.common.generic.toJson
import com.hivian.model.dto.database.DbPokemonStat

class PokemonStatListConverter {

    @TypeConverter
    fun objectToJson(value: List<DbPokemonStat>): String {
        return toJson(value)
    }

    @TypeConverter
    fun jsonToObject(value: String): List<DbPokemonStat> {
        return fromJson<List<DbPokemonStat>>(value)
    }
}
