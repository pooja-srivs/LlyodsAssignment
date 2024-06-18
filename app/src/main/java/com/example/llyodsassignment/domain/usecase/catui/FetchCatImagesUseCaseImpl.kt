package com.example.llyodsassignment.domain.usecase.catui

import com.example.llyodsassignment.data.repository.CatImagesRepository
import com.example.llyodsassignment.domain.mapper.toUiModel
import com.example.llyodsassignment.domain.model.DomainResult
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel
import com.example.llyodsassignment.ui.view.model.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCatImagesUseCaseImpl @Inject constructor(
    private val catImagesRepository: CatImagesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): FetchCatImagesUseCase {

    override suspend fun getCatImages(): Flow<UIState<List<CatImagesUiModel>>> =
        flow {
            try {
                catImagesRepository.getCatImages().collect { response ->
                    when(response) {
                        is DomainResult.Success -> {
                            emit(UIState.Success(response.value.toUiModel()))
                        }
                        is DomainResult.Failure -> {
                            emit(UIState.Failure(response.throwable))
                        }
                    }
                }
            } catch (throwable: Throwable) {
                emit(UIState.Failure(throwable))
            }
        }.flowOn(dispatcher)
}