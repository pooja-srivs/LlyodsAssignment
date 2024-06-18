package com.example.llyodsassignment.data.mapper

import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity
import com.example.llyodsassignment.data.model.CatImageResponseDto
import com.example.llyodsassignment.domain.model.CatImageResponse

fun List<CatImageResponseDto>.mapToDomain(): List<CatImageResponse> =
    this.map { item ->
        CatImageResponse(
            id = item.id ?: "",
            url = item.url ?: "",
            height = item.height ?: 0,
            width = item.width ?: 0
        )
    }


fun List<CatImageResponseDto>.mapToEntity(): List<CatImageResponseEntity> =
    this.map { item ->
        CatImageResponseEntity(
            id = item.id ?: "",
            url = item.url ?: "",
            width = item.width ?: 0,
            height = item.height ?: 0
        )
    }


fun List<CatImageResponseEntity>.toDomain(): List<CatImageResponse> =
    this.map { item ->
        CatImageResponse(
            id = item.id,
            url = item.url,
            width = item.width,
            height = item.height
        )
    }
