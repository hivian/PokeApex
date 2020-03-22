package com.hivian.model.mapper

import com.hivian.model.domain.Pokemon
import com.hivian.model.domain.PokemonSprites
import com.hivian.model.domain.PokemonStat
import com.hivian.model.domain.PokemonType
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.database.DbPokemonSprites
import com.hivian.model.dto.database.DbPokemonStat
import com.hivian.model.dto.database.DbPokemonType
import com.hivian.model.dto.network.NetworkPokemonObject

abstract class Mapper<I, O> {
    abstract fun map(input: I): O
    fun map(input: List<I>) : List<O> = input.map { map(it) }
}

data class MapperPokedexRepository(val remoteToDbMapper: MapperPokemonRemoteToDbImpl, val dbToDomainMapper: MapperPokemonDbToDomainImpl)

class MapperPokemonDbToDomainImpl : Mapper<DbPokemon, Pokemon>() {
    override fun map(input: DbPokemon): Pokemon {
        return Pokemon(
            name = input.name,
            height = input.height,
            weight = input.weight,
            abilities = input.abilities,
            forms = input.forms,
            moves = input.moves,
            sprites = PokemonSprites(input.sprites.frontDefault, input.sprites.backDefault),
            stats = input.stats.map { PokemonStat(it.name, it.baseStat, it.effort) },
            types = input.types.map { PokemonType(it.slot, it.name) })
    }
}

class MapperPokemonRemoteToDbImpl : Mapper<NetworkPokemonObject, DbPokemon>() {
    override fun map(input: NetworkPokemonObject): DbPokemon {
        return DbPokemon(
            id = input.id,
            name = input.name,
            height = input.height,
            weight = input.weight,
            abilities = input.abilities.map { it.ability.name },
            forms = input.forms.map { it.name },
            moves = input.moves.map { it.move.name },
            sprites = DbPokemonSprites(
                backDefault = input.sprites.backDefault,
                backDefaultFemale = input.sprites.backDefaultFemale,
                backShiny = input.sprites.backShiny,
                backShinyFemale = input.sprites.backShinyFemale,
                frontDefault = input.sprites.frontDefault,
                frontDefaultFemale = input.sprites.frontDefaultFemale,
                frontShiny = input.sprites.frontShiny,
                frontShinyFemale = input.sprites.frontShinyFemale),
            stats = input.stats.map { DbPokemonStat(
                name = it.stat.name,
                baseStat = it.baseStat,
                effort = it.effort
            ) },
            types = input.types.map { DbPokemonType(
                slot = it.slot,
                name = it.type.name
            ) }
        )
    }

}
