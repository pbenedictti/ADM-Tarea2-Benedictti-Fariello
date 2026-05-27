package com.misitioweb.admTarea2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.misitioweb.admTarea2.data.local.BookEntity
import com.misitioweb.admTarea2.data.remote.BookDto
import com.misitioweb.admTarea2.ui.BookViewModel
import com.misitioweb.admTarea2.ui.SearchType
import com.misitioweb.admTarea2.ui.components.BookItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(navController: NavController, viewModel: BookViewModel) {
    val results by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchType by viewModel.searchType.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    val searchLabel = when (searchType) {
        SearchType.TITLE -> "(título)"
        SearchType.AUTHOR -> "(autor)"
        SearchType.ISBN -> "(ISBN)"
    }

    ResultsScreenContent(
        results = results,
        isLoading = isLoading,
        searchQuery = searchQuery,
        searchLabel = searchLabel,
        favorites = favorites,
        onBackClick = { navController.popBackStack() },
        onFavoriteClick = { viewModel.toggleFavorite(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreenContent(
    results: List<BookDto>,
    isLoading: Boolean,
    searchQuery: String,
    searchLabel: String,
    favorites: List<BookEntity>,
    onBackClick: () -> Unit,
    onFavoriteClick: (BookDto) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Volver", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFF2C4CE), Color(0xFF8358E6))
                        )
                    )
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Resultado",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "$searchQuery $searchLabel",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${results.size} resultados encontrados",
                        fontSize = 16.sp,
                        color = Color.White
                    )

                }
            }


            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(results) { book ->
                        val isFav = favorites.any { it.id == book.key }
                        BookItem(
                            title = book.title,
                            author = book.authorNames?.firstOrNull() ?: "Unknown",
                            year = book.firstPublishYear?.toString() ?: "N/A",
                            coverUrl = book.coverI?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                            isFavorite = isFav,
                            onFavoriteClick = { onFavoriteClick(book) }
                        )
                    }
                }
            }
        }
    }
}
