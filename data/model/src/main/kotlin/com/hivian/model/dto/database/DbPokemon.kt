package com.hivian.model.dto.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.util.concurrent.TimeUnit

@Entity
data class DbPokemon(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    /**
     * Remote data
     */
    var pokemonId: Int,
    var name: String,
    var imageUrl: String,
    var height: Int = 0,
    var weight: Int = 0,
    var abilities: List<DbPokemonAbility> = emptyList(),
    var forms: List<String> = emptyList(),
    var moves: List<String> = emptyList(),
    var stats: List<DbPokemonStat> = emptyList(),
    var types: List<DbPokemonType> = emptyList(),

    /**
     * Local data
     */
    var favorite: Boolean = false,
    var caught: Boolean = false,
    var generation: Int? = null,
    var lastRefreshed: Date = Date()
) {
    /**
     * We consider that a [DbPokemon] is outdated when the last time
     * we fetched it was more than 10 minutes
     */
    fun haveToRefreshFromNetwork() : Boolean =
        TimeUnit.MILLISECONDS.toMinutes(Date().time - lastRefreshed.time) >= 1
}

