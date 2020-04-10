package com.hivian.model.domain

data class Pokemon(
    var pokemonId: Int,
    var name: String,
    var imageUrl: String,
    var height: Int = 0,
    var weight: Int = 0,
    var abilities: List<String> = emptyList(),
    var forms: List<String> = emptyList(),
    var moves: List<String> = emptyList(),
    var stats: List<PokemonStat> = emptyList(),
    var types: List<PokemonType> = emptyList(),
    var generation: Int? = null
)
