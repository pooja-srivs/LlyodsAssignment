package com.example.llyodsassignment.data.source.remote

import com.example.llyodsassignment.data.model.CatImageResponseDto

interface RemoteDataSource {

    suspend fun fetchCatImages() : List<CatImageResponseDto>
}