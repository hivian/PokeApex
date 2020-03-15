package com.hivian.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg users: T)

    @Delete
    suspend fun delete(vararg users: T)

}