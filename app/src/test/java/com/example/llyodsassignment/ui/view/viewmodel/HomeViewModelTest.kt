package com.example.llyodsassignment.ui.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.llyodsassignment.Mocks
import com.example.llyodsassignment.domain.usecase.catui.FetchCatImagesUseCase
import com.example.llyodsassignment.ui.view.effect.HomeEffect
import com.example.llyodsassignment.ui.view.intent.HomeIntent
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel
import com.example.llyodsassignment.ui.view.model.UIState
import com.example.llyodsassignment.ui.view.state.HomeState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fetchCatImagesUseCase: FetchCatImagesUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        fetchCatImagesUseCase = mockk()
        viewModel = HomeViewModel(fetchCatImagesUseCase)
    }

    @Test
    fun `success state is emitted correctly`() = runTest {
        val catImagesUiModel = Mocks.catImagesUiModel

        coEvery { fetchCatImagesUseCase.getCatImages() } answers  {
            flow { emit(UIState.Success(catImagesUiModel)) }
        }
        viewModel.handleIntents(HomeIntent.LoadCatImages)

        viewModel.catImagesStateFlow.test {
            val emittedItem = awaitItem()
            assertTrue(emittedItem is HomeState.Success)
            assertEquals(HomeState.Success(catImagesUiModel), emittedItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `failure state is emitted correctly`() = runTest {
        val exception = Throwable("Error occurred")
        coEvery { fetchCatImagesUseCase.getCatImages() } coAnswers {
            flow { emit(UIState.Failure(exception)) }
        }

        viewModel.handleIntents(HomeIntent.LoadCatImages)

        viewModel.catImagesStateFlow.test {
            val emittedItem = awaitItem()
            assertTrue(emittedItem is HomeState.Failure)
            assertEquals(HomeState.Failure(exception), emittedItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loading state is emitted correctly`() = runTest {
        coEvery { fetchCatImagesUseCase.getCatImages() } answers  {
            flow { emit(UIState.Loading) }
        }
        viewModel.handleIntents(HomeIntent.LoadCatImages)

        viewModel.catImagesStateFlow.test {
            val emittedItem = awaitItem()
            assertTrue(emittedItem is HomeState.Loading)
            assertEquals(HomeState.Loading, emittedItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when SelectCatImage is called, it should set selectedCatImagesModel and emit NavigateToDetail effect`() = runTest {
        val catImagesUiModel = CatImagesUiModel("1", "url1", 100, 200)

        viewModel.handleIntents(HomeIntent.SelectCatImage(catImagesUiModel))

        assertEquals(viewModel.selectedCatImagesModel, catImagesUiModel)

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.NavigateToDetail)
            assertEquals((effect as HomeEffect.NavigateToDetail).catImage, catImagesUiModel)
            cancelAndConsumeRemainingEvents()
        }
    }
}