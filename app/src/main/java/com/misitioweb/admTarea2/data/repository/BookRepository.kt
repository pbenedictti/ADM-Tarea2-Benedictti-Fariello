package com.misitioweb.admTarea2.data.repository

import com.misitioweb.admTarea2.data.local.BookDao
import com.misitioweb.admTarea2.data.local.BookEntity
import com.misitioweb.admTarea2.data.remote.OpenLibraryApi
import com.misitioweb.admTarea2.data.remote.SearchResponse
import kotlinx.coroutines.flow.Flow

class BookRepository(
    private val api: OpenLibraryApi,
    private val dao: BookDao
) {
    suspend fun searchBooks(query: String, searchByTitle: Boolean): SearchResponse {
        return if (searchByTitle) {
            api.searchBooks(title = query)
        } else {
            api.searchBooks(author = query)
        }
    }

    fun getFavorites(): Flow<List<BookEntity>> {
        return dao.getAllFavorites()
    }

    suspend fun addFavorite(book: BookEntity) {
        dao.insertFavorite(book)
    }

    suspend fun removeFavorite(book: BookEntity) {
        dao.deleteFavorite(book)
    }

    suspend fun isFavorite(id: String): Boolean {
        return dao.isFavorite(id)
    }
}
