package com.example.llyodsassignment.domain.mapper

import com.example.llyodsassignment.domain.model.CatImageResponse
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

fun List<CatImageResponse>.toUiModel(): List<CatImagesUiModel> =
    this.map { item ->
        CatImagesUiModel(
            id = item.id,
            url = item.url,
            height = item.height,
            width = item.width
        )
    }
