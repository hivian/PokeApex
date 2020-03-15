package com.hivian.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hivian.local.converter.*
import com.hivian.local.dao.PokedexDao
import com.hivian.model.dto.database.DbPokemon

@Database(entities = [DbPokemon::class], version = 1, exportSchema = false)
@TypeConverters(
    ListStringConverter::class,
    PokemonSpriteConverter::class,
    PokemonStatConverter::class,
    PokemonTypeConverter::class,
    DateConverter::class)
abstract class PokedexDatabase: RoomDatabase() {

    // DAO
    abstract fun pokedexDao(): PokedexDao

    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, PokedexDatabase::class.java, "pokedex.db")
                .build()
    }
}