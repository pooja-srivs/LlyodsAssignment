package com.example.llyodsassignment.di

import com.example.llyodsassignment.data.repository.CatImagesRepository
import com.example.llyodsassignment.data.repository.CatImagesRepositoryImpl
import com.example.llyodsassignment.data.source.local.LocalDataSource
import com.example.llyodsassignment.data.source.local.LocalDataSourceImpl
import com.example.llyodsassignment.data.source.remote.RemoteDataSource
import com.example.llyodsassignment.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsRemoteDataSource(dataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindsRepository(catImagesRepositoryImpl: CatImagesRepositoryImpl): CatImagesRepository
}