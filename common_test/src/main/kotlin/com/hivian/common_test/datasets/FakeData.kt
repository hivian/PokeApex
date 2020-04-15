package com.hivian.common_test.datasets

import com.hivian.model.domain.Pokemon
import com.hivian.model.domain.PokemonStats
import com.hivian.model.domain.PokemonTypes
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.database.DbPokemonStat
import com.hivian.model.dto.database.DbPokemonType
import com.hivian.model.dto.network.*
import java.util.*

object FakeData {

    fun createFakePokemonsNetwork(count: Int): List<NetworkPokemonObject> {
        return (0 until count).map { id ->
            createFakePokemonNetwork(
                id.toString(),
                id
            )
        }
    }

    fun createFakePokemonNetwork(name: String, id: Int = 0): NetworkPokemonObject {
        return NetworkPokemonObject(
            id = id, name = "Name_$name",
            height = 100, weight = 200,
            abilities = arrayListOf(NetworkPokemonAbilityObject(
                ability = NetworkPokemonAbility(name = "Fireball"),
                isHidden = false,
                slot = 0)),
            forms = arrayListOf(NetworkPokemonForm(name = "Circle")),
            moves = arrayListOf(NetworkPokemonMoveObject(NetworkPokemonMove(name = "Run"))),
            sprites = NetworkPokemonSprites("backDefault.jpg", null, "", null, "frontDefault.jpg", null, "", null),
            stats = arrayListOf(NetworkPokemonStatObject(
                baseStat = 0,
                effort = 1,
                stat = NetworkPokemonStat(name = "hp")
            )),
            types = arrayListOf(
                NetworkPokemonTypeObject(
                slot = 0,
                type = NetworkPokemonType(name = "Water")
            ))
        )
    }

    fun createFakePokemonsDb(count: Int): List<DbPokemon> {
        return (0 until count).map { id ->
            createFakePokemonDb(id)
        }
    }

    fun createFakePokemonDb(id: Int = 0): DbPokemon {
        return DbPokemon(
            id = id + 1,
            pokemonId = id,
            name = "Name_$id",
            height = 100, weight = 200,
            generation = 1,
            abilities = arrayListOf( "Fireball"),
            forms = arrayListOf("Circle"),
            moves = arrayListOf("Run"),
            imageUrl = "https://",
            stats = arrayListOf(DbPokemonStat(
                baseStat = 0,
                effort = 1,
                name = "Hp"
            )),
            types = arrayListOf(
                DbPokemonType(
                    slot = 0,
                    name = "Water"
                ))
        )
    }

    fun createFakePokemonsDomain(count: Int): List<Pokemon> {
        return (0 until count).map { id ->
            createFakePokemonDomain(id)
        }
    }

    fun createFakePokemonDomain(id: Int = 0): Pokemon {
        return Pokemon(pokemonId = id, name="pokemon_$id", ranking = id.toString().padStart(3,'0'),
            height = 50, weight = 100, abilities = listOf("ability$id"),
            forms =  listOf("form$id"), moves = listOf("moves$id"), imageUrl = "http://",
            stats = PokemonStats(),
            types = PokemonTypes())
    }

    private val DATE_REFRESH: Date = GregorianCalendar(2018, 5, 12).time
}