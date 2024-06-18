package com.example.llyodsassignment.data.repository

import app.cash.turbine.test
import com.example.llyodsassignment.Mocks
import com.example.llyodsassignment.data.mapper.mapToDomain
import com.example.llyodsassignment.data.mapper.mapToEntity
import com.example.llyodsassignment.data.mapper.toDomain
import com.example.llyodsassignment.data.repository.CatImagesRepositoryImpl.Companion.REFRESH_INTERVAL_MS
import com.example.llyodsassignment.data.source.local.LocalDataSource
import com.example.llyodsassignment.data.source.remote.RemoteDataSource
import com.example.llyodsassignment.domain.model.DomainResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CatImagesRepositoryImplTest {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repositoryImpl: CatImagesRepositoryImpl

    @Before
    fun setUp() {
        localDataSource = mockk()
        remoteDataSource = mockk()
        repositoryImpl = CatImagesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `isDataStale returns true if lastRefreshTime is zero`() {
        every { localDataSource.getLastRefreshTime() } returns 0L

        val result = repositoryImpl.isDataStale()

        Assert.assertTrue(result)
    }

    @Test
    fun `isDataStale returns true when lastRefreshTime is older than REFRESH_INTERVAL_MS`() {
        val lastRefreshTime = System.currentTimeMillis() - (REFRESH_INTERVAL_MS + 1)
        every { localDataSource.getLastRefreshTime() } returns lastRefreshTime

        val result = repositoryImpl.isDataStale()

        Assert.assertTrue(result)
    }

    @Test
    fun `isDataStale returns false when lastRefreshTime is within REFRESH_INTERVAL_MS`() {
        val lastRefreshTime = System.currentTimeMillis() + REFRESH_INTERVAL_MS
        every { localDataSource.getLastRefreshTime() } returns lastRefreshTime

        val result = repositoryImpl.isDataStale()

        Assert.assertFalse(result)
    }

    @Test
    fun `test getCatImages when data is stale`() = runTest{
        val expected = Mocks.catImageResponseDto

        val lastRefreshTime = 0L
        coEvery { remoteDataSource.fetchCatImages() } coAnswers { expected }
        every { localDataSource.saveLastRefreshTime(any()) } just runs
        coEvery { localDataSource.insertAllCatImageResponse(expected.mapToEntity()) } just runs
        every { localDataSource.getLastRefreshTime() } returns lastRefreshTime

        repositoryImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is DomainResult.Success)
            Assert.assertEquals(DomainResult.Success(expected.mapToDomain()), emittedItem)
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.fetchCatImages() }
        coVerify(exactly = 1) { localDataSource.saveLastRefreshTime(any()) }
        coVerify(exactly = 1) { localDataSource.insertAllCatImageResponse(expected.mapToEntity()) }
    }

    @Test
    fun `test getCatImages when data is fresh`() = runTest {
        val lastRefreshTime = System.currentTimeMillis()
        val expected = Mocks.catImageResponseEntity

        every { localDataSource.getLastRefreshTime() } returns lastRefreshTime
        coEvery { localDataSource.getAllCatImageResponse() } coAnswers {
            flow { emit(expected) }
        }

        repositoryImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is DomainResult.Success)
            Assert.assertEquals(DomainResult.Success(expected.toDomain()), emittedItem)
            awaitComplete()
        }

        coVerify(exactly = 1) { localDataSource.getLastRefreshTime() }
        coVerify(exactly = 1) { localDataSource.getAllCatImageResponse() }
    }

    @Test
    fun `test getCatImages emits Failure when api throws error`() = runTest{
        val expected = Throwable()
        val pastTime = System.currentTimeMillis() - (REFRESH_INTERVAL_MS + 1)
        coEvery { remoteDataSource.fetchCatImages() } throws expected
        every { localDataSource.getLastRefreshTime() } returns pastTime

        repositoryImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is DomainResult.Failure)
            Assert.assertEquals(DomainResult.Failure(expected), emittedItem)
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.fetchCatImages() }
        coVerify(exactly = 1) { localDataSource.getLastRefreshTime() }
    }

    @Test
    fun `test getAllCatImageResponse emits Failure when database throws error`() = runTest{
        val expected = Throwable()
        val pastTime = System.currentTimeMillis() + REFRESH_INTERVAL_MS
        coEvery { localDataSource.getAllCatImageResponse() } throws expected
        every { localDataSource.getLastRefreshTime() } returns pastTime

        repositoryImpl.getCatImages().test {
            val emittedItem = awaitItem()
            Assert.assertTrue(emittedItem is DomainResult.Failure)
            Assert.assertEquals(DomainResult.Failure(expected), emittedItem)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { localDataSource.getAllCatImageResponse() }
        coVerify(exactly = 1) { localDataSource.getLastRefreshTime() }
    }
}