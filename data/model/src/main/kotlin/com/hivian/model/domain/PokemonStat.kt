package com.hivian.model.domain

data class PokemonStat(
    val name: Type,
    val baseStat: Int,
    val effort: Int
) {
    enum class Type(name: String) {
        HP("hp"),
        ATK("attack"),
        DEF("defense"),
        SP_ATK("special-attack"),
        SP_DEF("special-defense"),
        SPD("speed")
    }
}
