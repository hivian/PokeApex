package com.hivian.model.domain

data class PokemonTypes(
    var slot1: String,
    var slot2: String
) {
    companion object {
        fun empty() : PokemonTypes = PokemonTypes("", "")
    }
}
