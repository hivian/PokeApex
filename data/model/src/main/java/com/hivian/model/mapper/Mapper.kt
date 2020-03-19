package com.hivian.model.mapper

import com.hivian.model.domain.Pokemon
import com.hivian.model.domain.PokemonSprites
import com.hivian.model.domain.PokemonStat
import com.hivian.model.domain.PokemonType
import com.hivian.model.dto.database.DbPokemon

interface Mapper<I, O> {
    fun map(input: I): O
    fun map(input: List<I>) : List<O>
}

class MapperPokemonDbToDomainImpl : Mapper<DbPokemon, Pokemon> {
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

    override fun map(input: List<DbPokemon>) : List<Pokemon> {
        return input.map { map(it) }
    }
}