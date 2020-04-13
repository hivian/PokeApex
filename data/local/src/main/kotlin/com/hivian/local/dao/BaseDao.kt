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
    suspend fun update(vararg pokemons: T): Int

    @Delete
    suspend fun delete(vararg pokemons: T): Int
}
