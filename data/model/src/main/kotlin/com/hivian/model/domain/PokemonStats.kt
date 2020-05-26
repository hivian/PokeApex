package com.hivian.model.domain

data class PokemonStats(
    var hp: Int,
    var attack: Int,
    var defense: Int,
    var specialAttack: Int,
    var specialDefense: Int,
    var speed: Int
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

    companion object {
        fun empty(): PokemonStats = PokemonStats(0, 0, 0, 0, 0, 0)
    }
}
