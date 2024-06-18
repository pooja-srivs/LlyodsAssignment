package com.example.llyodsassignment.data.source.remote

import com.example.llyodsassignment.Mocks
import com.example.llyodsassignment.data.apiservice.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RemoteDataSourceImplTest {

    private lateinit var apiService: ApiService
    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @Before
    fun setUp() {
        apiService = mockk()
        remoteDataSourceImpl = RemoteDataSourceImpl(apiService)
    }

    @Test
    fun `test fetchCatImages returns expected data`() = runTest {
        val expected = Mocks.catImageResponseDto
        coEvery { apiService.fetchCatImagesData() } returns expected

        val result = remoteDataSourceImpl.fetchCatImages()

        Assert.assertEquals(expected, result)
        coVerify(exactly = 1) { apiService.fetchCatImagesData() }
    }

    @Test(expected = Exception::class)
    fun `test fetchCatImages returns Exception when ApiService fails`() = runTest{
        val exception = Exception("API call failed")
        coEvery { apiService.fetchCatImagesData() } throws exception

        remoteDataSourceImpl.fetchCatImages()
    }
}