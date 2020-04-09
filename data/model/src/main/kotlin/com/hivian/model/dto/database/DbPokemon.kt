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
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val forms: List<String>,
    val moves: List<String>,
    val imageUrl: String,
    val stats: List<DbPokemonStat>,
    val types: List<DbPokemonType>,

    val generation: Int?,
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

