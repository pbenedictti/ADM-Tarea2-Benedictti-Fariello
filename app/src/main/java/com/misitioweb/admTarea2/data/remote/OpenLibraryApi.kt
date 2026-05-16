package com.misitioweb.admTarea2.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("title") title: String? = null,
        @Query("author") author: String? = null
    ): SearchResponse

    companion object {
        const val BASE_URL = "https://openlibrary.org/"
    }
}
