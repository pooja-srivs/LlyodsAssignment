package com.example.llyodsassignment.data.source.local

import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getLastRefreshTime(): Long

    fun saveLastRefreshTime(lastRefreshTime: Long)

    suspend fun insertAllCatImageResponse(entities: List<CatImageResponseEntity>)

    fun getAllCatImageResponse(): Flow<List<CatImageResponseEntity>>
}