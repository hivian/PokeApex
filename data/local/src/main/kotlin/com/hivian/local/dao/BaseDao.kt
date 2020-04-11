package com.hivian.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: List<T>): LongArray

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pokemons: T): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pokemons: List<T>): Int

    @Delete
    suspend fun delete(pokemons: T): Int

    @Delete
    suspend fun delete(pokemons: List<T>): Int
}
