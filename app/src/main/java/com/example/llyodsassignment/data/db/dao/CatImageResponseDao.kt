package com.example.llyodsassignment.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatImageResponseDao {

    @Query("SELECT * FROM cat_images")
    fun getAllCatImageResponse(): Flow<List<CatImageResponseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatImageResponse(catImageResponseEntity: List<CatImageResponseEntity>)

    @Query("DELETE FROM cat_images")
    suspend fun clear()
}