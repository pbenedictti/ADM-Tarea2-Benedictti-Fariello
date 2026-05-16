package com.misitioweb.admTarea2.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM favorite_books")
    fun getAllFavorites(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(book: BookEntity)

    @Delete
    suspend fun deleteFavorite(book: BookEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_books WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}
