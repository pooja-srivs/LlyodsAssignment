package com.example.llyodsassignment.di

import android.content.Context
import androidx.room.Room
import com.example.llyodsassignment.data.db.AppDatabase
import com.example.llyodsassignment.data.db.dao.CatImageResponseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context, AppDatabase::class.java,
            "cat_images_db"
        ).build()
    }

    @Provides
    fun provideCatImageResponseDao(database: AppDatabase): CatImageResponseDao = database.catImageResponseDao()
}