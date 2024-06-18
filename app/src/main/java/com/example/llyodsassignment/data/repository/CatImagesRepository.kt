package com.example.llyodsassignment.data.repository

import com.example.llyodsassignment.domain.model.CatImageResponse
import com.example.llyodsassignment.domain.model.DomainResult
import kotlinx.coroutines.flow.Flow

interface CatImagesRepository {

    suspend fun getCatImages(): Flow<DomainResult<List<CatImageResponse>>>
}