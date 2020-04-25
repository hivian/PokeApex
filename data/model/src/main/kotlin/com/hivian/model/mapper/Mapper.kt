package com.hivian.model.mapper

import com.hivian.model.domain.Pokemon
import com.hivian.model.domain.PokemonAbilities
import com.hivian.model.domain.PokemonStats
import com.hivian.model.domain.PokemonTypes
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.database.DbPokemonAbility
import com.hivian.model.dto.database.DbPokemonStat
import com.hivian.model.dto.database.DbPokemonType
import com.hivian.model.dto.network.NetworkPokemon
import com.hivian.model.dto.network.NetworkPokemonObject

abstract class Mapper<I, O> {
    /**
     * Mapping object.
     *
     * @param from Initial object to from mapping.
     * @return An Object containing the results of applying the transformation.
     */
    abstract fun map(input: I): O

    fun map(input: List<I>) : List<O> = input.map { map(it) }
}

data class MapperPokedexRepository(
    val remoteToDbMapper: MapperPokemonRemoteToDbImpl,
    val dbToDomainMapper: MapperPokemonDbToDomainImpl,
    val networkToObjectImpl : MapperNetworkPokemonToObjectImpl)

class MapperPokemonDbToDomainImpl : Mapper<DbPokemon, Pokemon>() {
    override fun map(input: DbPokemon): Pokemon {
        return Pokemon(
            pokemonId = input.pokemonId,
            name = input.name,
            height = input.height,
            weight = input.weight,
            generation = input.generation,
            abilities = PokemonAbilities().apply {
                input.abilities.map {
                    when (it.slot) {
                        1 -> slot1 = it.name
                        2 -> slot2 = it.name
                        3 -> slot3 = it.name
                    }
                }
            },
            forms = input.forms,
            moves = input.moves,
            imageUrl = input.imageUrl,
            stats = PokemonStats().apply {
                input.stats.map {
                    when (it.name) {
                        "hp" -> hp = it.baseStat
                        "attack" -> attack = it.baseStat
                        "defense" -> defense = it.baseStat
                        "special-attack" -> specialAttack = it.baseStat
                        "special-defense" -> specialDefense = it.baseStat
                        "speed" -> speed = it.baseStat
                    }
                }
            },
            types = PokemonTypes().apply {
                input.types.map {
                    when (it.slot) {
                        1 -> slot1 = it.name
                        2 -> slot2 = it.name
                    }
                }
            },
            favorite = input.favorite,
            caught = input.caught
        )
    }
}

class MapperPokemonRemoteToDbImpl : Mapper<NetworkPokemonObject, DbPokemon>() {
    override fun map(input: NetworkPokemonObject): DbPokemon {
        return DbPokemon(
            pokemonId = input.id,
            name = input.name,
            height = input.height,
            weight = input.weight,
            generation = Generations.from(input.id),
            abilities = input.abilities.map { DbPokemonAbility(
                it.slot,
                it.isHidden,
                it.ability.name)
            },
            forms = input.forms.map { it.name },
            moves = input.moves.map { it.move.name },
            // High resolution image source.
            imageUrl = "https://pokeres.bastionbot.org/images/pokemon/${input.id}.png",
            stats = input.stats.map { DbPokemonStat(
                name = it.stat.name,
                baseStat = it.baseStat,
                effort = it.effort
            ) },
            types = input.types.map { DbPokemonType(
                slot = it.slot,
                name = it.type.name
            )}
        )
    }
}

class MapperNetworkPokemonToObjectImpl : Mapper<NetworkPokemon, NetworkPokemonObject>() {
    override fun map(input: NetworkPokemon): NetworkPokemonObject {
        return NetworkPokemonObject().apply {
            // Pokemon id is at the end of the Pokemon url
            id = input.url.trimEnd('/').substringAfterLast("/").toInt()
            name = input.name
        }
    }
}
