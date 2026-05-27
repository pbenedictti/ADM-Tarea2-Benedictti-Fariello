package com.misitioweb.admTarea2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.misitioweb.admTarea2.ui.BookViewModel
import com.misitioweb.admTarea2.ui.Screen
import com.misitioweb.admTarea2.ui.screens.*
import com.misitioweb.admTarea2.ui.theme.AdmTarea2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: BookViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            AdmTarea2Theme(darkTheme = isDarkMode) {
                MainApp(viewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: BookViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        Screen.Search,
        Screen.Favorites,
        Screen.Settings
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                if (currentDestination?.route != Screen.Splash.route) {
                    NavigationBar {
                        items.forEach { screen ->
                            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true ||
                                    (screen == Screen.Search && currentDestination?.route == Screen.Results.route)
                            NavigationBarItem(
                                icon = { Icon(screen.icon, contentDescription = screen.title) },
                                label = { Text(screen.title) },
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color(0xFF8358E6),
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    indicatorColor = Color(0xFFEEC0CF).copy(alpha = 0.3f),
                                    selectedTextColor = Color(0xFF8358E6)
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Splash.route) {
                    SplashScreen(navController)
                }
                composable(Screen.Search.route) {
                    HomeScreen(navController, viewModel)
                }
                composable(Screen.Results.route) {
                    ResultsScreen(navController, viewModel)
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(navController, viewModel)
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(navController, viewModel)
                }
            }
        }
    }
}
