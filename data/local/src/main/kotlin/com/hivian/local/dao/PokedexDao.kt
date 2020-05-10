package com.hivian.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.hivian.model.dto.database.DbPokemon
import java.util.*

@Dao
abstract class PokedexDao : BaseDao<DbPokemon> {

    @Query("SELECT * FROM DbPokemon ORDER BY pokemonId ASC")
    abstract suspend fun getAllPokemon(): List<DbPokemon>

    @Query("SELECT * FROM DbPokemon WHERE name LIKE '%' || :pattern || '%' ORDER BY pokemonId ASC")
    abstract fun getAllPokemonPatternLive(pattern: String): LiveData<List<DbPokemon>>

    @Query("SELECT * FROM DbPokemon WHERE favorite = 1 ORDER BY pokemonId ASC")
    abstract fun getAllPokemonFavoritesPatternLive(): LiveData<List<DbPokemon>>

    @Query("SELECT * FROM DbPokemon WHERE caught = 1 ORDER BY pokemonId ASC")
    abstract fun getAllPokemonCaughtPatternLive(): LiveData<List<DbPokemon>>

    @Query("SELECT * FROM DbPokemon ORDER BY pokemonId ASC")
    abstract fun getAllPokemonLive(): LiveData<List<DbPokemon>>

    @Query("SELECT * FROM DbPokemon WHERE name = :name LIMIT 1")
    abstract fun getPokemonDetailLive(name: String): LiveData<DbPokemon>

    @Query("SELECT * FROM DbPokemon WHERE favorite = 1 ORDER BY pokemonId ASC")
    abstract fun getPokemonFavoritesLive(): LiveData<List<DbPokemon>>

    @Query("SELECT * FROM DbPokemon WHERE caught = 1 ORDER BY pokemonId ASC")
    abstract fun getPokemonCaughtLive(): LiveData<List<DbPokemon>>

    @Query("SELECT * FROM DbPokemon WHERE pokemonId = :pokemonId LIMIT 1")
    abstract suspend fun getPokemonByServerId(pokemonId: Int): DbPokemon?

    @Query("SELECT * FROM DbPokemon WHERE name = :name LIMIT 1")
    abstract suspend fun getPokemonByName(name: String): DbPokemon

    @Query("UPDATE DbPokemon SET favorite = :favorite WHERE pokemonId = :pokemonId")
    abstract suspend fun updatePokemonFavoriteStatus(pokemonId: Int, favorite: Boolean): Int

    @Query("UPDATE DbPokemon SET caught = :caught WHERE pokemonId = :pokemonId")
    abstract suspend fun updatePokemonCaughtStatus(pokemonId: Int, caught: Boolean): Int

    @Query("DELETE FROM DbPokemon")
    abstract suspend fun deleteAll() : Int

    @Transaction
    open suspend fun upsertPreview(pokemon: DbPokemon) {
        val isPokemon = getPokemonByServerId(pokemon.pokemonId)
        isPokemon?.let {
            update(isPokemon.apply {
                pokemonId = pokemon.pokemonId
                name = pokemon.name
                imageUrl = pokemon.imageUrl
            })
        } ?: run {
            insert(pokemon)
        }
    }

    @Transaction
    open suspend fun upsertPreview(pokemons: List<DbPokemon>) {
        pokemons.forEach { upsertPreview(it) }
    }

    @Transaction
    open suspend fun upsertDetail(pokemon: DbPokemon) {
        val isPokemon = getPokemonByServerId(pokemon.pokemonId)
        isPokemon?.let {
            update(pokemon.apply {
                id = isPokemon.id
                favorite = isPokemon.favorite
                caught = isPokemon.caught
            })
        } ?: run {
            insert(pokemon)
        }
    }

    @Transaction
    open suspend fun upsertDetail(pokemons: List<DbPokemon>) {
        pokemons.forEach { upsertDetail(it) }
    }

    // ---

    /**
     * Each time we save a pokemon, we update its 'lastRefreshed' field
     * This allows us to know when we have to refresh its data
     */

    suspend fun savePokemonPreview(pokemon: DbPokemon) {
        upsertPreview(pokemon.apply { lastRefreshed = Date() })
    }

    suspend fun savePokemonPreview(pokemons: List<DbPokemon>) {
        upsertPreview(pokemons.apply { forEach { it.lastRefreshed = Date() } })
    }

    suspend fun savePokemonDetail(pokemon: DbPokemon) {
        upsertDetail(pokemon.apply { lastRefreshed = Date() })
    }

    suspend fun savePokemonDetail(pokemons: List<DbPokemon>) {
        upsertDetail(pokemons.apply { forEach { it.lastRefreshed = Date() } })
    }
    
}
