package com.hivian.repository.utils

import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.database.DbPokemonSprites
import com.hivian.model.dto.database.DbPokemonStat
import com.hivian.model.dto.database.DbPokemonType
import com.hivian.model.dto.network.*
import java.util.*

object FakeData {

    fun createFakePokemonsNetwork(count: Int): List<NetworkPokemonObject> {
        return (0 until count).map { id ->
            createFakePokemonNetwork(id.toString(), id)
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

    fun createFakePokemonDb(id: Int): DbPokemon {
        return DbPokemon(
            pokemonId = id,
            name = "Name_$id",
            height = 100, weight = 200,
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

    private val DATE_REFRESH: Date = GregorianCalendar(2018, 5, 12).time
}