package com.misitioweb.admTarea2.ui

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.misitioweb.admTarea2.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationFlow() {
        // 1. Initial State: Home Screen
        composeTestRule.onNodeWithText("Mi Biblioteca").assertIsDisplayed()
        
        // 2. Navigate to Favorites (Bottom Navigation Tab)
        // Usamos hasAnyAncestor con isRoot para evitar problemas de tipos, o simplemente filtramos por Role
        composeTestRule.onNode(hasText("Favoritos") and hasRole(Role.Tab)).performClick()
        composeTestRule.onNodeWithText("Mis Favoritos").assertIsDisplayed()
        
        // 3. Navigate to Settings (Bottom Navigation Tab)
        composeTestRule.onNode(hasText("Configuración") and hasRole(Role.Tab)).performClick()
        composeTestRule.onNodeWithText("Apariencia").assertIsDisplayed()
        
        // 4. Back to Home (Search Tab)
        composeTestRule.onNode(hasText("Buscar") and hasRole(Role.Tab)).performClick()
        composeTestRule.onNodeWithText("Mi Biblioteca").assertIsDisplayed()
    }

    @Test
    fun testSearchTypeSelection() {
        // Open search type dropdown
        composeTestRule.onNodeWithText("Buscar por: Título").performClick()
        
        // Select ISBN
        composeTestRule.onNodeWithText("ISBN").performClick()
        
        // Verify placeholder changed
        composeTestRule.onNodeWithText("Buscar por ISBN...").assertIsDisplayed()
        
        // Verify dropdown text changed
        composeTestRule.onNodeWithText("Buscar por: ISBN").assertIsDisplayed()
    }

    @Test
    fun testSearchNavigationToResults() {
        // Enter a query
        composeTestRule.onNodeWithText("Buscar por título...").performTextInput("9780747532699")
        
        // Perform search (Click the BUTTON, not the TAB)
        composeTestRule.onNode(hasText("Buscar") and hasRole(Role.Button)).performClick()
        
        // Verify results screen
        composeTestRule.onNodeWithText("Resultados").assertIsDisplayed()
        composeTestRule.onNodeWithText("9780747532699 (título)").assertIsDisplayed()
    }
}

// Helper para filtrar por Role ya que a veces el matcher nativo puede fallar o no estar disponible
fun hasRole(role: Role): SemanticsMatcher {
    return SemanticsMatcher.expectValue(SemanticsProperties.Role, role)
}
