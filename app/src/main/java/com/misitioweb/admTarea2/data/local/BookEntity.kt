package com.misitioweb.admTarea2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val year: String,
    val coverId: String?,
    val isFavorite: Boolean = true
)
