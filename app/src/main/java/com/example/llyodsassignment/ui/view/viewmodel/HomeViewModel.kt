package com.example.llyodsassignment.ui.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.llyodsassignment.domain.usecase.catui.FetchCatImagesUseCase
import com.example.llyodsassignment.ui.view.effect.HomeEffect
import com.example.llyodsassignment.ui.view.intent.HomeIntent
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel
import com.example.llyodsassignment.ui.view.model.UIState
import com.example.llyodsassignment.ui.view.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCatImagesUseCase: FetchCatImagesUseCase
): ViewModel() {

    var selectedCatImagesModel by mutableStateOf<CatImagesUiModel?>(null)
        private set

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val catImagesMutableStateFlow: MutableStateFlow<HomeState> =
        MutableStateFlow(HomeState.Loading)
    val catImagesStateFlow: StateFlow<HomeState> by lazy { catImagesMutableStateFlow }

    init {
        handleIntents(HomeIntent.LoadCatImages)
    }

    fun handleIntents(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadCatImages -> {
                loadCatImages()
            }

            is HomeIntent.SelectCatImage -> {
                selectCatImage(intent.catImageModel)
            }
        }
    }

    private fun loadCatImages() {
        viewModelScope.launch {
            fetchCatImagesUseCase.getCatImages()
                .onStart { catImagesMutableStateFlow.value = HomeState.Loading }
                .catch { catImagesMutableStateFlow.value = HomeState.Failure(it) }
                .collect { response ->
                    when(response) {
                        is UIState.Loading -> {
                            catImagesMutableStateFlow.value = HomeState.Loading
                        }

                        is UIState.Failure -> {
                            catImagesMutableStateFlow.value = HomeState.Failure(response.throwable)
                        }

                        is UIState.Success -> {
                            catImagesMutableStateFlow.value = HomeState.Success(response.value)
                        }
                    }
                }
        }
    }

    private fun selectCatImage(catImagesUiModel: CatImagesUiModel) {
        this.selectedCatImagesModel = catImagesUiModel
        viewModelScope.launch {
            _effect.send(HomeEffect.NavigateToDetail(catImagesUiModel))
        }
    }
}