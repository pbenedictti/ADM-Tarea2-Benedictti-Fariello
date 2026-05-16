package com.misitioweb.admTarea2.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

    val searchLabel = if (searchType == SearchType.TITLE) "(título)" else "(autor)"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Resultados", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("$searchQuery $searchLabel", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "${results.size} resultados encontrados",
                modifier = Modifier.padding(16.dp),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

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
                            onFavoriteClick = { viewModel.toggleFavorite(book) }
                        )
                    }
                }
            }
        }
    }
}
