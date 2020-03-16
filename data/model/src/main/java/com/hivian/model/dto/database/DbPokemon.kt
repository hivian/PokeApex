package com.hivian.model.dto.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hivian.model.domain.Pokemon
import com.hivian.model.domain.PokemonSprites
import com.hivian.model.domain.PokemonStat
import com.hivian.model.domain.PokemonType
import java.util.Date
import java.util.concurrent.TimeUnit

@Entity
data class DbPokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val forms: List<String>,
    val moves: List<String>,
    val sprites: DbPokemonSprites,
    val stats: List<DbPokemonStat>,
    val types: List<DbPokemonType>,

    var lastRefreshed: Date = Date()
) {
    /**
     * We consider that a [DbPokemon] is outdated when the last time
     * we fetched it was more than 10 minutes
     */
    fun haveToRefreshFromNetwork() : Boolean =
            TimeUnit.MILLISECONDS.toMinutes(Date().time - lastRefreshed.time) >= 10
}

fun DbPokemon.mapToDomain(): Pokemon = Pokemon(
    name = name,
    height = height,
    weight = weight,
    abilities = abilities,
    forms = forms,
    moves = moves,
    sprites = PokemonSprites(sprites.frontDefault, sprites.backDefault),
    stats = stats.map { PokemonStat(it.name, it.baseStat, it.effort) },
    types = types.map { PokemonType(it.slot, it.name) }
)

fun List<DbPokemon>.mapToDomain(): List<Pokemon> = map { it.mapToDomain() }
