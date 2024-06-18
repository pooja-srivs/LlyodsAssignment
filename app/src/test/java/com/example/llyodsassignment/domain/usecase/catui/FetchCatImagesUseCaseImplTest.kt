package com.example.llyodsassignment.domain.usecase.catui

import app.cash.turbine.test
import com.example.llyodsassignment.Mocks
import com.example.llyodsassignment.data.repository.CatImagesRepository
import com.example.llyodsassignment.domain.mapper.toUiModel
import com.example.llyodsassignment.domain.model.CatImageResponse
import com.example.llyodsassignment.domain.model.DomainResult
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel
import com.example.llyodsassignment.ui.view.model.UIState
import com.nhaarman.mockitokotlin2.mock
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class FetchCatImagesUseCaseImplTest {

    private lateinit var fetchCatImagesUseCaseImpl: FetchCatImagesUseCaseImpl
    private lateinit var catImagesRepository: CatImagesRepository

    @Before
    fun setUp() {
        catImagesRepository = mockk()
        fetchCatImagesUseCaseImpl = FetchCatImagesUseCaseImpl(catImagesRepository)
    }

    @Test
    fun `test getCatImages returns UIState Success`() = runTest {
        val catImageResponse = Mocks.catImageResponse
        val expected = UIState.Success(catImageResponse.toUiModel())

        coEvery { catImagesRepository.getCatImages() } coAnswers {
            flow { emit(DomainResult.Success(catImageResponse)) }
        }

        fetchCatImagesUseCaseImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is UIState.Success)
            Assert.assertEquals(expected, emittedItem)
            awaitComplete()
        }
    }

    @Test
    fun `test FetchCatImagesUseCaseImpl returns UIState Failure`() = runTest {
        val exception = RuntimeException("Failed to fetch data")
        val expected = UIState.Failure(exception)

        coEvery { catImagesRepository.getCatImages() } coAnswers {
            flow { emit(DomainResult.Failure(exception)) }
        }

        fetchCatImagesUseCaseImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is UIState.Failure)
            Assert.assertEquals(expected, emittedItem)
            awaitComplete()
        }
    }

    @Test
    fun `test FetchCatImagesUseCaseImpl returns Failure`() = runTest {
        val exception = RuntimeException("unexpected error")
        val expected = UIState.Failure(exception)

        coEvery { catImagesRepository.getCatImages() } coAnswers {
            flow { emit(DomainResult.Failure(exception)) }
        }

        fetchCatImagesUseCaseImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is UIState.Failure)
            Assert.assertEquals(expected, emittedItem)
            awaitComplete()
        }
    }

    @Test
    fun `getCatImages test coroutine cancellation`() = runTest {
        val catImageResponse = Mocks.catImageResponse
        val expected = UIState.Success(catImageResponse.toUiModel())

        coEvery { catImagesRepository.getCatImages() } coAnswers {
            flow { emit(DomainResult.Success(catImageResponse)) }
        }

        val job = launch {
            fetchCatImagesUseCaseImpl.getCatImages().test {
                val emittedItem = awaitItem()
                Assert.assertTrue(emittedItem is UIState.Success)
                Assert.assertEquals(expected, emittedItem)
                awaitComplete()
            }
        }
        job.cancelAndJoin()

        job.invokeOnCompletion { reason ->
            Assert.assertTrue(reason is CancellationException)
        }
    }

    @Test
    fun `test getCatImages returns delayed response`() = runTest {
        val catImageResponse = Mocks.catImageResponse
        val expected = UIState.Success(catImageResponse.toUiModel())

        coEvery { catImagesRepository.getCatImages() } coAnswers {
            delay(2000)
            flow { emit(DomainResult.Success(catImageResponse)) }
        }

        fetchCatImagesUseCaseImpl.getCatImages().test {
                val emittedItem = awaitItem()
                Assert.assertTrue(emittedItem is UIState.Success)
                Assert.assertEquals(expected, emittedItem)
                awaitComplete()
        }
    }

    @Test
    fun `test getCatImages returns empty list`() = runTest {
        val catImageResponse = emptyList<CatImageResponse>()
        val expected = UIState.Success(catImageResponse.toUiModel())

        coEvery { catImagesRepository.getCatImages() } coAnswers {
            flow { emit(DomainResult.Success(catImageResponse)) }
        }

        fetchCatImagesUseCaseImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is UIState.Success)
            Assert.assertEquals(expected, emittedItem)
            awaitComplete()
        }
    }
}