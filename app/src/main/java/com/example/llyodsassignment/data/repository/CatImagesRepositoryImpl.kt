package com.example.llyodsassignment.data.repository

import com.example.llyodsassignment.data.mapper.mapToDomain
import com.example.llyodsassignment.data.mapper.mapToEntity
import com.example.llyodsassignment.data.mapper.toDomain
import com.example.llyodsassignment.data.source.local.LocalDataSource
import com.example.llyodsassignment.data.source.remote.RemoteDataSource
import com.example.llyodsassignment.domain.model.CatImageResponse
import com.example.llyodsassignment.domain.model.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class CatImagesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): CatImagesRepository {

    companion object {
        const val REFRESH_INTERVAL_MS = 30 * 60 * 1000
    }

    override suspend fun getCatImages(): Flow<DomainResult<List<CatImageResponse>>> =
        flow {
            try {
                if (isDataStale()) {
                    val response = remoteDataSource.fetchCatImages()
                    localDataSource.saveLastRefreshTime(System.currentTimeMillis())
                    localDataSource.insertAllCatImageResponse(response.mapToEntity())
                    emit(DomainResult.Success(response.mapToDomain()))
                } else {
                    localDataSource.getAllCatImageResponse().collect{ cachedData ->
                        emit(DomainResult.Success(cachedData.toDomain()))
                    }
                }
            } catch (e: Throwable) {
                emit(DomainResult.Failure(e))
            }
        }.flowOn(dispatcher)

    @VisibleForTesting
    internal fun isDataStale(): Boolean {
        val lastRefreshTime= localDataSource.getLastRefreshTime() ?: return true
        if (lastRefreshTime == 0L) {
            return true
        }
        val result = System.currentTimeMillis() - lastRefreshTime > REFRESH_INTERVAL_MS
        return result
    }
}
