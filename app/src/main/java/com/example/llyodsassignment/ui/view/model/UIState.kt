package com.example.llyodsassignment.ui.view.model

import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

sealed class UIState<out T> {
    data class Success<out T>(val value: T): UIState<T>()
    data class Failure(val throwable: Throwable): UIState<Nothing>()
    object Loading: UIState<Nothing>()
}