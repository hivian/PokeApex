package com.hivian.model.domain

data class PokemonStats(
    var hp: Int = 0,
    var attack: Int = 0,
    var defense: Int = 0,
    var specialAttack: Int = 0,
    var specialDefense: Int = 0,
    var speed: Int = 0
) {
    fun hpPercent() = hp.statToPercent()
    fun attackPercent() = attack.statToPercent()
    fun defensePercent() = defense.statToPercent()
    fun specialAttackPercent() = specialAttack.statToPercent()
    fun specialDefensePercent() = specialDefense.statToPercent()
    fun speedPercent() = speed.statToPercent()

    fun sum(): Int = hp + attack + defense + specialAttack + specialDefense + speed
    fun sumPercent(): Int = sum().sumToPercent()

    private fun Int.statToPercent() = 100 * this / 255
    private fun Int.sumToPercent() = 100 * this / 600
}
