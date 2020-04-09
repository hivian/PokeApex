package com.hivian.model.mapper

enum class Generations(val rawValue : IntRange) {
    I(1..151),
    II(152..251),
    III(252..386),
    IV(387..493),
    V(494..649),
    VI(650..721),
    VII(722..807); // 809 ?

    companion object {
        /**
         * Get generation number from pokemonId, null if pokemonId is not referenced.
         * @param pokemonId The Pokemon id
         */
        fun from(pokemonId: Int) : Int? = try {
            values().first { generations -> pokemonId in generations.rawValue }.ordinal + 1
        } catch (e: NoSuchElementException) {
            null
        }
    }

}