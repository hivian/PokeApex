package com.hivian.model.domain

data class Pokemon(
    var pokemonId: Int,
    var name: String,
    var imageUrl: String,
    var height: Int = 0,
    var weight: Int = 0,
    var abilities: PokemonAbilities = PokemonAbilities.EMPTY,
    var forms: List<String> = emptyList(),
    var moves: List<String> = emptyList(),
    var stats: PokemonStats = PokemonStats.EMPTY,
    var types: PokemonTypes = PokemonTypes.EMPTY,
    var favorite: Boolean = false,
    var caught: Boolean = false,
    var generation: Int? = null
)
