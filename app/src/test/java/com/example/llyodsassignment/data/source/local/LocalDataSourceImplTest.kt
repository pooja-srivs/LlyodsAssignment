package com.example.llyodsassignment.data.source.local

import app.cash.turbine.test
import com.example.llyodsassignment.Mocks
import com.example.llyodsassignment.common.PreferenceHelper
import com.example.llyodsassignment.data.db.dao.CatImageResponseDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalDataSourceImplTest {

    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var catImageResponseDao: CatImageResponseDao
    private lateinit var localDataSourceImpl: LocalDataSourceImpl

    @Before
    fun setUp() {
        preferenceHelper = mockk()
        catImageResponseDao = mockk()
        localDataSourceImpl = LocalDataSourceImpl(preferenceHelper, catImageResponseDao)
    }

    @Test
    fun `test getLastRefreshTime returns valid timestamp`() {
        val expectedTime = 3687698798
        every { preferenceHelper.getLastRefreshTime() } returns expectedTime
        val result = localDataSourceImpl.getLastRefreshTime()
        Assert.assertEquals(expectedTime, result)
        coVerify(exactly = 1) { preferenceHelper.getLastRefreshTime() }
    }

    @Test
    fun `test getLastRefreshTime returns negative timestamp`() {
        val expected = -1000L
        every { preferenceHelper.getLastRefreshTime() } returns expected
        val result = localDataSourceImpl.getLastRefreshTime()
        Assert.assertEquals(expected, result)
        coVerify(exactly = 1) { preferenceHelper.getLastRefreshTime() }
    }

    @Test
    fun `test saveLastRefreshTime saves valid timestamp`() {
        val lastRefreshTime = 3687698798
        every { preferenceHelper.saveLastRefreshTime(any()) } just runs
        localDataSourceImpl.saveLastRefreshTime(lastRefreshTime)

        coVerify(exactly = 1) { preferenceHelper.saveLastRefreshTime(lastRefreshTime) }
    }

    @Test
    fun `test insertAll inserts and fetches correct data`() = runTest {
        val input = Mocks.catImageResponseEntity
        coEvery { catImageResponseDao.insertCatImageResponse(any()) } just runs
        coEvery { catImageResponseDao.clear() } just runs

        localDataSourceImpl.insertAllCatImageResponse(input)

        coVerify(exactly = 1) { catImageResponseDao.clear() }
        coVerify(exactly = 1) { catImageResponseDao.insertCatImageResponse(input) }
    }

    @Test
    fun `test getAllCatImageResponse fetches correct data`() = runTest {
        val expected = Mocks.catImageResponseEntity
        coEvery { catImageResponseDao.getAllCatImageResponse() } returns flow {
            emit(expected)
        }
        localDataSourceImpl.getAllCatImageResponse().test {
            val emittedItem = awaitItem()
            assertEquals(expected.size, emittedItem.size)
            assertEquals(expected.first(), emittedItem.first())
            assertEquals(expected, emittedItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getCatImageResponse throws Exception`() = runTest {
        val exception = RuntimeException("Database error")
        coEvery { catImageResponseDao.getAllCatImageResponse() } returns  flow { throw exception }

        localDataSourceImpl.getAllCatImageResponse().test {
            val emittedError = awaitError()
            assertEquals(exception.message, emittedError.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test insertAll inserts fresh data and clears the stale data and returns correct data`() = runTest {
        val input = Mocks.catImageResponseEntity
        val updatedInput = Mocks.updatedCatImageResponseEntity
        coEvery { catImageResponseDao.insertCatImageResponse(any()) } just runs
        coEvery { catImageResponseDao.clear() } just runs
        coEvery { catImageResponseDao.getAllCatImageResponse() } returns flow {
            emit(updatedInput)
        }

        localDataSourceImpl.insertAllCatImageResponse(input)
        localDataSourceImpl.insertAllCatImageResponse(updatedInput)

        localDataSourceImpl.getAllCatImageResponse().test {
            val item = awaitItem()
            assertEquals(updatedInput.first(), item.first())
            assertEquals(updatedInput.size, item.size)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 2) { catImageResponseDao.clear() }
        coVerify(exactly = 1) { catImageResponseDao.insertCatImageResponse(input) }
    }
}