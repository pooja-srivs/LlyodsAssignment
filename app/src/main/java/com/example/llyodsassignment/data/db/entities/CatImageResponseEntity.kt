package com.example.llyodsassignment.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_images")
data class CatImageResponseEntity(
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
)