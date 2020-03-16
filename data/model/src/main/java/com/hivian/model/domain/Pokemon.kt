package com.hivian.model.domain

class Pokemon(
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val forms: List<String>,
    val moves: List<String>,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>
)
