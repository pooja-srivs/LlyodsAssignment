package com.example.llyodsassignment.ui.view.intent

import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

sealed class HomeIntent {
    object LoadCatImages: HomeIntent()
    data class SelectCatImage(val catImageModel: CatImagesUiModel): HomeIntent()
}