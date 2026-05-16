package com.misitioweb.admTarea2.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDaoTest {

    private lateinit var database: BookDatabase
    private lateinit var dao: BookDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.bookDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetAllFavorites() = runBlocking {
        val book = BookEntity("1", "Test Book", "Author", "2021", null)
        dao.insertFavorite(book)
        val favorites = dao.getAllFavorites().first()
        assertEquals(1, favorites.size)
        assertEquals(book, favorites[0])
    }

    @Test
    fun deleteFavorite() = runBlocking {
        val book = BookEntity("1", "Test Book", "Author", "2021", null)
        dao.insertFavorite(book)
        dao.deleteFavorite(book)
        val favorites = dao.getAllFavorites().first()
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun isFavorite() = runBlocking {
        val book = BookEntity("1", "Test Book", "Author", "2021", null)
        dao.insertFavorite(book)
        assertTrue(dao.isFavorite("1"))
    }
}
