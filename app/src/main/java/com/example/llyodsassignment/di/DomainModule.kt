package com.example.llyodsassignment.di

import com.example.llyodsassignment.domain.usecase.catui.FetchCatImagesUseCase
import com.example.llyodsassignment.domain.usecase.catui.FetchCatImagesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindsCatImageResponseUseCase(fetchCatImagesUseCaseImpl: FetchCatImagesUseCaseImpl): FetchCatImagesUseCase

}