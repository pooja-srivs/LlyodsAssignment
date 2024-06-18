package com.example.llyodsassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.llyodsassignment.data.db.dao.CatImageResponseDao
import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity

@Database(entities = [CatImageResponseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun catImageResponseDao(): CatImageResponseDao
}