package com.example.llyodsassignment.data.source.local

import com.example.llyodsassignment.common.PreferenceHelper
import com.example.llyodsassignment.data.db.dao.CatImageResponseDao
import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val preferenceHelper: PreferenceHelper,
    private val catImageResponseDao: CatImageResponseDao
): LocalDataSource {

    override fun getLastRefreshTime(): Long =
        preferenceHelper.getLastRefreshTime()

    override fun saveLastRefreshTime(lastRefreshTime: Long) =
        preferenceHelper.saveLastRefreshTime(lastRefreshTime)

    override suspend fun insertAllCatImageResponse(entities: List<CatImageResponseEntity>) {
        catImageResponseDao.clear()
        catImageResponseDao.insertCatImageResponse(entities)
    }

    override fun getAllCatImageResponse(): Flow<List<CatImageResponseEntity>> =
        catImageResponseDao.getAllCatImageResponse()
}