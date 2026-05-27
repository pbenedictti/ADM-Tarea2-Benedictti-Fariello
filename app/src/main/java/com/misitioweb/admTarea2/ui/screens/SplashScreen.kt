package com.misitioweb.admTarea2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.misitioweb.admTarea2.R
import com.misitioweb.admTarea2.ui.Screen
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.misitioweb.admTarea2.ui.theme.AdmTarea2Theme

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000)

        navController.navigate(Screen.Search.route) {
            popUpTo(route = Screen.Splash.route) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF3E5F5),
                        Color(0xFFE1BEE7)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Contenedor de la ilustración
        Surface(
            modifier = Modifier
                .size(200.dp)
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White.copy(alpha = 0.9f),
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icono de librería
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground_image),
                    contentDescription = "Biblioteca",
                    modifier = Modifier.size(450.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Texto "Mi Biblioteca"
                Text(
                    text = "Mi Biblioteca",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF673AB7)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Indicadores de página
        Row(
            modifier = Modifier.padding(bottom = 48.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Primer punto (activo)
            Surface(
                modifier = Modifier
                    .size(12.dp)
                    .padding(4.dp),
                shape = RoundedCornerShape(50),
                color = Color(0xFF673AB7)
            ) {}

            // Segundo punto (inactivo)
            Surface(
                modifier = Modifier
                    .size(12.dp)
                    .padding(4.dp),
                shape = RoundedCornerShape(50),
                color = Color(0xFF673AB7).copy(alpha = 0.3f)
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    AdmTarea2Theme {
        SplashScreen(navController = navController)
    }
}

