package com.misitioweb.admTarea2.data.repository

import com.misitioweb.admTarea2.data.local.BookDao
import com.misitioweb.admTarea2.data.remote.OpenLibraryApi
import com.misitioweb.admTarea2.data.remote.SearchResponse
import com.misitioweb.admTarea2.ui.SearchType
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class BookRepositoryTest {

    private lateinit var repository: BookRepository
    private val api: OpenLibraryApi = mock()
    private val dao: BookDao = mock()

    @Before
    fun setup() {
        repository = BookRepository(api, dao)
    }

    @Test
    fun `searchBooks with TITLE calls api with title parameter`() {
        runBlocking {
            val query = "The Hobbit"
            val expectedResponse = SearchResponse(1, emptyList())
            whenever(api.searchBooks(title = query)).thenReturn(expectedResponse)

            repository.searchBooks(query, SearchType.TITLE)

            verify(api).searchBooks(title = query)
        }
    }

    @Test
    fun `searchBooks with AUTHOR calls api with author parameter`() {
        runBlocking {
            val query = "Tolkien"
            val expectedResponse = SearchResponse(1, emptyList())
            whenever(api.searchBooks(author = query)).thenReturn(expectedResponse)

            repository.searchBooks(query, SearchType.AUTHOR)

            verify(api).searchBooks(author = query)
        }
    }

    @Test
    fun `searchBooks with ISBN calls api with isbn parameter`() {
        runBlocking {
            val query = "9780547928227"
            val expectedResponse = SearchResponse(1, emptyList())
            whenever(api.searchBooks(isbn = query)).thenReturn(expectedResponse)

            repository.searchBooks(query, SearchType.ISBN)

            verify(api).searchBooks(isbn = query)
        }
    }
}
