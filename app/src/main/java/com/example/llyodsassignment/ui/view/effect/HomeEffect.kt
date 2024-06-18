package com.example.llyodsassignment.ui.view.effect

import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

sealed class HomeEffect {
    data class NavigateToDetail(val catImage: CatImagesUiModel) : HomeEffect()
}