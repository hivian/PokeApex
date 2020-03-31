package com.hivian.local.converter

import androidx.room.TypeConverter
import com.hivian.common.generic.fromJson
import com.hivian.common.generic.toJson
import com.hivian.model.dto.database.DbPokemonSprites

class PokemonSpriteConverter {

    @TypeConverter
    fun objectToJson(value: DbPokemonSprites): String {
        return toJson(value)
    }

    @TypeConverter
    fun jsonToObject(value: String): DbPokemonSprites {
        return fromJson<DbPokemonSprites>(value)
    }
}
