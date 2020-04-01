package com.hivian.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.hivian.model.dto.database.DbPokemon
import java.util.*

@Dao
abstract class PokedexDao : BaseDao<DbPokemon> {

    @Query("SELECT * FROM DbPokemon ORDER BY name ASC LIMIT :ascLimit")
    abstract suspend fun getTopPokemons(ascLimit : Int = 20): List<DbPokemon>

    @Query("SELECT * FROM DbPokemon WHERE pokemonId = :pokemonId LIMIT 1")
    abstract suspend fun getPokemonById(pokemonId: Int): DbPokemon?

    @Query("SELECT * FROM DbPokemon WHERE name = :name LIMIT 1")
    abstract suspend fun getPokemonByName(name: String): DbPokemon

    @Transaction
    open suspend fun upsert(pokemon: DbPokemon) {
        val isPokemon = getPokemonById(pokemon.pokemonId)
        if (isPokemon != null) {
            update(pokemon)
        } else {
            insert(pokemon)
        }
    }

    @Transaction
    open suspend fun upsert(pokemons: List<DbPokemon>) {
        pokemons.forEach { upsert(it) }
    }

    // ---

    /**
     * Each time we save a pokemon, we update its 'lastRefreshed' field
     * This allows us to know when we have to refresh its data
     */

    suspend fun save(pokemon: DbPokemon) {
        upsert(pokemon.apply { lastRefreshed = Date() })
    }

    suspend fun save(pokemons: List<DbPokemon>) {
        upsert(pokemons.apply { forEach { it.lastRefreshed = Date() } })
    }
}
