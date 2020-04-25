package com.hivian.local.converter

import androidx.room.TypeConverter
import com.hivian.common.generic.fromJson
import com.hivian.common.generic.toJson
import com.hivian.model.dto.database.DbPokemonAbility

class PokemonAbilityListConverter {

    @TypeConverter
    fun objectToJson(value: List<DbPokemonAbility>): String {
        return toJson(value)
    }

    @TypeConverter
    fun jsonToObject(value: String): List<DbPokemonAbility> {
        return fromJson<List<DbPokemonAbility>>(value)
    }
}
