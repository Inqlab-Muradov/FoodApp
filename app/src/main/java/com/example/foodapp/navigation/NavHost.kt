package com.example.foodapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.presentation.login.LoginScreen
import com.example.foodapp.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SplashScreen,
    ) {
        composable<ScreenRoutes.SplashScreen> {
            SplashScreen(
                navigateToLogin = {
                    navController.navigate(ScreenRoutes.LoginScreen)
                }
            )
        }
        composable<ScreenRoutes.LoginScreen> {
            LoginScreen()
        }
    }

}

@Serializable
sealed class ScreenRoutes {

    @Serializable
    data object SplashScreen : ScreenRoutes()

    @Serializable
    data object LoginScreen : ScreenRoutes()
}