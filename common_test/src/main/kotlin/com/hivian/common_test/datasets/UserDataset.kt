package com.hivian.common_test.datasets

import com.hivian.model.domain.Pokemon
import com.hivian.model.domain.PokemonSprites
import com.hivian.model.domain.PokemonStat
import com.hivian.model.domain.PokemonType

object UserDataset {

    //val DATE_REFRESH: Date = GregorianCalendar(2018, 5, 12).time
    val FAKE_POKEMONS = listOf(
        Pokemon(pokemonId = 1, name="pokemon1", height = 50, weight = 100, abilities = listOf("ability1"),
            forms =  listOf("form1"), moves = listOf("moves1"), sprites = PokemonSprites(
                backDefault = "BackDefault1",
                frontDefault = "FrontDefault1"
            ),
            stats = listOf(PokemonStat(baseStat = 2, effort = 3, name = "Type1")),
            types = listOf(PokemonType(slot = 1, name = "Type1"))),
        Pokemon(pokemonId = 2, name="pokemon2", height = 50, weight = 200, abilities = listOf("ability2"),
            forms =  listOf("form2"), moves = listOf("moves2"), sprites = PokemonSprites(
                backDefault = "BackDefault2",
                frontDefault = "FrontDefault2"
            ),
            stats = listOf(PokemonStat(baseStat = 2, effort = 3, name = "Type2")),
            types = listOf(PokemonType(slot = 2, name = "Type2"))),
        Pokemon(pokemonId = 3, name="pokemon3", height = 50, weight = 300, abilities = listOf("ability3"),
            forms =  listOf("form3"), moves = listOf("moves3"), sprites = PokemonSprites(
                backDefault = "BackDefault3",
                frontDefault = "FrontDefault3"
            ),
            stats = listOf(PokemonStat(baseStat = 2, effort = 3, name = "Type3")),
            types = listOf(PokemonType(slot = 3, name = "Type3")))
    )
}