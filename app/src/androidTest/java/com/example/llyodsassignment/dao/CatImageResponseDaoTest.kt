package com.example.llyodsassignment.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.llyodsassignment.Mocks
import com.example.llyodsassignment.data.db.AppDatabase
import com.example.llyodsassignment.data.db.dao.CatImageResponseDao
import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CatImageResponseDaoTest {

    private lateinit var dao: CatImageResponseDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.catImageResponseDao()
    }

    @Test
    fun testInsertAndRetrieveCatImageResponseInEmptyDatabase() = runTest {
        dao.getAllCatImageResponse().test {
            val emittedResult = awaitItem()
            Assert.assertEquals(0, emittedResult.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testInsertAndRetrieveCatImageResponseEmptyList() = runTest {
        val input = emptyList<CatImageResponseEntity>()
        dao.insertCatImageResponse(input)

        dao.getAllCatImageResponse().test {
            val emittedResult = awaitItem()
            Assert.assertEquals(0, emittedResult.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testInsertAllCatImageResponse()= runTest {
        val input = Mocks.catImageResponseEntity
        dao.insertCatImageResponse(input)

        dao.getAllCatImageResponse().test {
            val result = awaitItem()
            assertEquals(input.size, result.size)
            assertEquals(input.first(), result.first())
            assertEquals(input, result)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testDeletesAllCatImageResponse() = runTest {
        val catImageResponseEntity = Mocks.catImageResponseEntity
        dao.insertCatImageResponse(catImageResponseEntity)
        dao.clear()
        dao.getAllCatImageResponse().test {
            val emittedItem = awaitItem()
            assertEquals(0, emittedItem.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testClearOnEmptyDatabase() = runTest {
        dao.clear()
        val result = dao.getAllCatImageResponse().first()
        assertEquals(0, result.size)
    }

    @After
    fun tearDown() {
        database.close()
    }
}