package com.example.llyodsassignment.apiservice

import com.example.llyodsassignment.Helper
import com.example.llyodsassignment.Mocks.mockResponseBody
import com.example.llyodsassignment.data.apiservice.ApiService
import com.example.llyodsassignment.data.model.CatImageResponseDto
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build().create(ApiService::class.java)
    }

    private fun parseCatImageResponse(mockResponseBody: String): List<CatImageResponseDto> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter<List<CatImageResponseDto>>(Types.newParameterizedType(List::class.java, CatImageResponseDto::class.java))
        return jsonAdapter.fromJson(mockResponseBody) ?: emptyList()
    }

    @Test
    fun `test fetchCatImagesData expected mock response`() = runTest {
        val mockResponse = MockResponse().apply {
            setResponseCode(200)
            setBody(mockResponseBody)
        }
        mockWebServer.enqueue(mockResponse)

        val expected = parseCatImageResponse(mockResponseBody)

        val response = apiService.fetchCatImagesData()
        mockWebServer.takeRequest()

        Assert.assertEquals(expected, response)
    }

    @Test
    fun `test fetchCatImagesData returns expected json response`() = runTest {
        val content = Helper.readFileResources("/cat_images_response.json")
        val mockResponse = MockResponse().apply {
            setResponseCode(200)
            setBody(content)
        }
        mockWebServer.enqueue(mockResponse)

        val expected = parseCatImageResponse(content)

        val response = apiService.fetchCatImagesData()
        mockWebServer.takeRequest()

        Assert.assertEquals(expected, response)
    }

    @Test(expected = HttpException::class)
    fun `test fetchCatImagesData throws Exception`() = runTest {
        val mockResponse = MockResponse().apply {
            setResponseCode(404)
            setBody("Something went wrong")
        }
        mockWebServer.enqueue(mockResponse)

        val response = apiService.fetchCatImagesData()
        mockWebServer.takeRequest()

        Assert.assertEquals("Something went wrong", response)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}