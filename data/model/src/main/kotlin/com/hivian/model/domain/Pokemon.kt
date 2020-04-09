package com.hivian.model.domain

class Pokemon(
    val pokemonId: Int,
    val name: String,
    val imageUrl: String,
    val height: Int = 0,
    val weight: Int = 0,
    val abilities: List<String> = emptyList(),
    val forms: List<String> = emptyList(),
    val moves: List<String> = emptyList(),
    val stats: List<PokemonStat> = emptyList(),
    val types: List<PokemonType> = emptyList(),
    val generation: Int? = null
)
