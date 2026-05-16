package com.misitioweb.admTarea2.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String): SearchResponse

    companion object {
        const val BASE_URL = "https://openlibrary.org/"
    }
}
