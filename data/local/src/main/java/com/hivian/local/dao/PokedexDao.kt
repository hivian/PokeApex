package com.hivian.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.hivian.model.dto.database.DbPokemon
import java.util.Date

@Dao
abstract class PokedexDao : BaseDao<DbPokemon> {

    @Query("SELECT * FROM DbPokemon ORDER BY name ASC LIMIT 20")
    abstract suspend fun getTopPokemons(): List<DbPokemon>

    @Query("SELECT * FROM DbPokemon WHERE name = :name LIMIT 1")
    abstract suspend fun getPokemon(name: String): DbPokemon

    // ---

    /**
     * Each time we save a pokemon, we update its 'lastRefreshed' field
     * This allows us to know when we have to refresh its data
     */

    suspend fun save(pokemon: DbPokemon) {
        insert(pokemon.apply { lastRefreshed = Date() })
    }

    suspend fun save(pokemons: List<DbPokemon>) {
        insert(*pokemons.apply { forEach { it.lastRefreshed = Date() } }.toTypedArray())
    }
}
