package com.misitioweb.admTarea2.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.misitioweb.admTarea2.data.local.BookDao
import com.misitioweb.admTarea2.data.local.BookDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class BookViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    
    private lateinit var viewModel: BookViewModel
    private val application: Application = mock()
    private val database: BookDatabase = mock()
    private val dao: BookDao = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        whenever(database.bookDao()).thenReturn(dao)
        whenever(dao.getAllFavorites()).thenReturn(flowOf(emptyList()))

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() {

    }
}
