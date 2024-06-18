package com.example.llyodsassignment.data.apiservice

import com.example.llyodsassignment.data.model.CatImageResponseDto
import retrofit2.http.GET

interface ApiService {

    @GET("v1/images/search?limit=20")
    suspend fun fetchCatImagesData() : List<CatImageResponseDto>
}