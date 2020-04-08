package com.hivian.local.dao

import androidx.room.*

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg users: T)

    @Delete
    suspend fun delete(vararg users: T)
}
