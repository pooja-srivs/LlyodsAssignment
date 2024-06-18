package com.example.llyodsassignment.ui.view.state

import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

sealed class HomeState {
    data class Success(val value: List<CatImagesUiModel>): HomeState()
    data class Failure(val throwable: Throwable): HomeState()
    object Loading: HomeState()
}