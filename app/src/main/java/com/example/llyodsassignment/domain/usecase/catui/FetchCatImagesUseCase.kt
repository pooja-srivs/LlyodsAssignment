package com.example.llyodsassignment.domain.usecase.catui

import com.example.llyodsassignment.ui.view.model.CatImagesUiModel
import com.example.llyodsassignment.ui.view.model.UIState
import kotlinx.coroutines.flow.Flow

interface FetchCatImagesUseCase {

    suspend fun getCatImages(): Flow<UIState<List<CatImagesUiModel>>>
}