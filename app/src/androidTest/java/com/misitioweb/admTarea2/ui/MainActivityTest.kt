package com.misitioweb.admTarea2.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
    fun testSearchNavigation() {
        // Check if Home Screen is displayed
        composeTestRule.onNodeWithText("Mi Biblioteca").assertIsDisplayed()
        
        // Enter text and click search
        composeTestRule.onNodeWithText("Buscar por título...").performTextInput("rolon")
        composeTestRule.onNodeWithText("Buscar").performClick()
        
        // Check if Results Screen header is displayed
        composeTestRule.onNodeWithText("Resultados").assertIsDisplayed()
    }

    @Test
    fun testBottomNavigation() {
        // Go to Favorites
        composeTestRule.onNodeWithText("Favoritos").performClick()
        composeTestRule.onNodeWithText("Mis Favoritos").assertIsDisplayed()
        
        // Go to Settings
        composeTestRule.onNodeWithText("Configuración").performClick()
        composeTestRule.onNodeWithText("Apariencia").assertIsDisplayed()
    }
}
