package com.hivian.model.dto.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.*
import java.util.concurrent.TimeUnit

@Entity
data class DbPokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val pokemonId: Int,
    val name: String,
    val imageUrl: String,
    val height: Int = 0,
    val weight: Int = 0,
    val abilities: List<String> = emptyList(),
    val forms: List<String> = emptyList(),
    val moves: List<String> = emptyList(),
    val stats: List<DbPokemonStat> = emptyList(),
    val types: List<DbPokemonType> = emptyList(),

    val generation: Int? = null,
    var new: Boolean = true,
    var lastRefreshed: Date = Date()
) {
    /**
     * We consider that a [DbPokemon] is outdated when the last time
     * we fetched it was more than 10 minutes
     */
    fun haveToRefreshFromNetwork() : Boolean =
            TimeUnit.MILLISECONDS.toMinutes(Date().time - lastRefreshed.time) >= 30
}

