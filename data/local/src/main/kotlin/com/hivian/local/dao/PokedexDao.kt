package com.hivian.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.hivian.model.dto.database.DbPokemon
import java.util.*

@Dao
abstract class PokedexDao : BaseDao<DbPokemon> {

    @Query("SELECT * FROM DbPokemon ORDER BY pokemonId ASC")
    abstract suspend fun getTopPokemons(): List<DbPokemon>

    @Query("SELECT * FROM DbPokemon WHERE pokemonId = :pokemonId LIMIT 1")
    abstract suspend fun getPokemonByServerId(pokemonId: Int): DbPokemon?

    @Query("SELECT * FROM DbPokemon WHERE name = :name LIMIT 1")
    abstract suspend fun getPokemonByName(name: String): DbPokemon

    @Query("SELECT * FROM DbPokemon WHERE name LIKE '%' || :pattern || '%'")
    abstract suspend fun getPokemonListByPattern(pattern: String): List<DbPokemon>

    @Query("SELECT * FROM DbPokemon WHERE favorite = 1")
    abstract suspend fun getPokemonFavorites(): List<DbPokemon>

    @Query("SELECT * FROM DbPokemon WHERE caught = 1")
    abstract suspend fun getPokemonCaught(): List<DbPokemon>

    @Query("UPDATE DbPokemon SET favorite = :favorite WHERE pokemonId = :pokemonId")
    abstract suspend fun updatePokemonFavoriteStatus(pokemonId: Int, favorite: Boolean): Int

    @Query("UPDATE DbPokemon SET caught = :caught WHERE pokemonId = :pokemonId")
    abstract suspend fun updatePokemonCaughtStatus(pokemonId: Int, caught: Boolean): Int

    @Query("DELETE FROM DbPokemon")
    abstract suspend fun deleteAll() : Int

    @Transaction
    open suspend fun upsert(pokemon: DbPokemon) {
        val isPokemon = getPokemonByServerId(pokemon.pokemonId)
        isPokemon?.let {
            update(pokemon.apply { id = isPokemon.id })
        } ?: run {
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
