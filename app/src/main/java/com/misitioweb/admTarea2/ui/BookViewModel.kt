package com.misitioweb.admTarea2.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.misitioweb.admTarea2.components.BookService
import com.misitioweb.admTarea2.components.FavoriteReceiver
import com.misitioweb.admTarea2.data.local.BookDatabase
import com.misitioweb.admTarea2.data.local.BookEntity
import com.misitioweb.admTarea2.data.remote.BookDto
import com.misitioweb.admTarea2.data.remote.OpenLibraryApi
import com.misitioweb.admTarea2.data.repository.BookRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class SearchType {
    TITLE, AUTHOR, ISBN
}

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository
    
    private val _searchResults = MutableStateFlow<List<BookDto>>(emptyList())
    val searchResults: StateFlow<List<BookDto>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchType = MutableStateFlow(SearchType.TITLE)
    val searchType: StateFlow<SearchType> = _searchType

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    val favorites: StateFlow<List<BookEntity>>

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled

    }

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "AdmTarea2App/1.0")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

        val api = Retrofit.Builder()
            .baseUrl(OpenLibraryApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenLibraryApi::class.java)

        val dao = BookDatabase.getDatabase(application).bookDao()
        repository = BookRepository(api, dao)

        favorites = repository.getFavorites()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSearchTypeChange(type: SearchType) {
        _searchType.value = type
    }

    fun searchBooks() {
        val query = _searchQuery.value
        if (query.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.searchBooks(query, _searchType.value)
                _searchResults.value = response.docs
                
                // Start Service
                val intent = Intent(getApplication(), BookService::class.java).apply {
                    putExtra("query", query)
                }
                getApplication<Application>().startService(intent)
                
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(book: BookDto) {
        viewModelScope.launch {
            val id = book.key
            val isFav = repository.isFavorite(id)
            val entity = BookEntity(
                id = id,
                title = book.title,
                author = book.authorNames?.firstOrNull() ?: "Unknown",
                year = book.firstPublishYear?.toString() ?: "N/A",
                coverId = book.coverI?.toString()
            )

            if (isFav) {
                repository.removeFavorite(entity)
            } else {
                repository.addFavorite(entity)
                
                // Send Broadcast
                val intent = Intent(FavoriteReceiver.ACTION_FAVORITE_ADDED).apply {
                    putExtra("book_title", book.title)
                    `package` = getApplication<Application>().packageName
                }
                getApplication<Application>().sendBroadcast(intent)
            }
        }
    }

    fun removeFavorite(book: BookEntity) {
        viewModelScope.launch {
            repository.removeFavorite(book)
        }
    }
}
