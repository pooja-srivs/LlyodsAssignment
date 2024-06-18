package com.example.llyodsassignment.data.source.remote

import com.example.llyodsassignment.data.apiservice.ApiService
import com.example.llyodsassignment.data.model.CatImageResponseDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun fetchCatImages(): List<CatImageResponseDto> = apiService.fetchCatImagesData()
}