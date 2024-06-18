package com.example.llyodsassignment.di

import android.content.Context
import com.example.llyodsassignment.BuildConfig
import com.example.llyodsassignment.data.apiservice.ApiManager
import com.example.llyodsassignment.data.apiservice.ApiService
import com.example.llyodsassignment.data.apiservice.interceptor.NetworkInterceptor
import com.example.llyodsassignment.data.interceptor.ApiKeyInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesCoroutineDispatcher() : CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun providesNetworkInterceptor(@ApplicationContext context: Context) : NetworkInterceptor = NetworkInterceptor(context)

    @Singleton
    @Provides
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Singleton
    @Provides
    fun providesApiKeyInterceptor(apiKey: String): ApiKeyInterceptor = ApiKeyInterceptor(apiKey)

    @Singleton
    @Provides
    fun providesOkHttp(networkInterceptor: NetworkInterceptor, apiKeyInterceptor: ApiKeyInterceptor) : OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(apiKeyInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient, moshi: Moshi) : Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(retrofit: Retrofit) : ApiManager = ApiManager(retrofit)

    @Singleton
    @Provides
    fun providesApiService(apiManager: ApiManager) : ApiService = apiManager.apiService
}