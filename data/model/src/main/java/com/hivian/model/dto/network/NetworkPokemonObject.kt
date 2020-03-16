package com.hivian.model.dto.network

import com.google.gson.annotations.SerializedName
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.database.DbPokemonSprites
import com.hivian.model.dto.database.DbPokemonStat
import com.hivian.model.dto.database.DbPokemonType

data class NetworkPokemonObject(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("abilities")
    val abilities: List<NetworkPokemonAbilityObject>,

    @SerializedName("forms")
    val forms: List<NetworkPokemonForm>,

    @SerializedName("moves")
    val moves: List<NetworkPokemonMoveObject>,

    @SerializedName("sprites")
    val sprites: NetworkPokemonSprites,

    @SerializedName("stats")
    val stats: List<NetworkPokemonStatObject>,

    @SerializedName("types")
    val types: List<NetworkPokemonTypeObject>

)

fun NetworkPokemonObject.mapToDb() : DbPokemon {
    return DbPokemon(
        name = name,
        height = height,
        weight = weight,
        abilities = abilities.map { it.ability.name },
        forms = forms.map { it.name },
        moves = moves.map { it.move.name },
        sprites = DbPokemonSprites(
            backDefault = sprites.backDefault,
            backDefaultFemale = sprites.backDefaultFemale,
            backShiny = sprites.backShiny,
            backShinyFemale = sprites.backShinyFemale,
            frontDefault = sprites.frontDefault,
            frontDefaultFemale = sprites.frontDefaultFemale,
            frontShiny = sprites.frontShiny,
            frontShinyFemale = sprites.frontShinyFemale),
        stats = stats.map { DbPokemonStat(
            name = it.stat.name,
            baseStat = it.baseStat,
            effort =  it.effort
        )},
        types = types.map { DbPokemonType(
            slot = it.slot,
            name = it.type.name
        )}
    )
}

fun List<NetworkPokemonObject>.mapToDb(): List<DbPokemon> {
    return map { it.mapToDb() }
}