package com.adm.adm_tarea2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adm.adm_tarea2.ui.theme.*

// 1. Data Class
data class BookUiModel(
    val title: String,
    val author: String,
    val year: String,
    val isFavorite: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADMTarea2Theme {
                ResultsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen() {
    val mockBooks = listOf(
        BookUiModel("El Duelo", "Gabriel Rolón", "2021", true),
        BookUiModel("La Voz Ausente - Gabriel Rolon", "ROLON", "2014"),
        BookUiModel("Encuentros Lado B Del Amor Booket", "Gabriel Rolón", "2013"),
        BookUiModel("La respuesta está en ti", "Gabriel Rolón", "2011"),
        BookUiModel("Felicidad: Más Allá de la Ilusión", "Gabriel Rolón", "2010")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Resultados",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MainText
                            )
                        )
                        Text(
                            text = "rolon (autor)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SecondaryText
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = MainText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { BottomNavigationBar() },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "20 resultados encontrados",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryText)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(mockBooks) { index, book ->
                    BookCard(book = book)
                }
            }
        }
    }
}

@Composable
fun BookCard(book: BookUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Portada Placeholder
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 90.dp)
                    .background(SoftBorder, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
               /* Icon(
                    Icons.Default.Book, contentDescription = null, tint = SecondaryText,
                    modifier = TODO()
                )*/
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MainText
                    ),
                    maxLines = 2
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryText)
                )
                Text(
                    text = book.year,
                    style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                )
            }

            IconButton(onClick = { /* TODO: Toggle Favorite */ }) {
                Icon(
                    imageVector = if (book.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (book.isFavorite) FavoriteColor else SecondaryText
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
            label = { Text("Buscar") },
            selected = true,
            onClick = { /* TODO */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryPurple,
                selectedTextColor = PrimaryPurple,
                indicatorColor = BackgroundLight
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = false,
            onClick = { /* TODO */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Settings, contentDescription = "Configuración") },
            label = { Text("Configuración") },
            selected = false,
            onClick = { /* TODO */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    ADMTarea2Theme {
        ResultsScreen()
    }
}
