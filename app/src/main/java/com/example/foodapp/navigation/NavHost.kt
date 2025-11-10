package com.example.foodapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.presentation.home.HomeScreen
import com.example.foodapp.presentation.home.HomeViewModel
import com.example.foodapp.presentation.login.LoginScreen
import com.example.foodapp.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.HomeScreen,
    ) {
        composable<ScreenRoutes.SplashScreen> {
            SplashScreen(
                navigateToLogin = {
                    navController.navigate(ScreenRoutes.LoginScreen)
                }
            )
        }
        composable<ScreenRoutes.LoginScreen> {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(ScreenRoutes.HomeScreen)
                }
            )
        }

        composable<ScreenRoutes.HomeScreen> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val uiState by homeViewModel.homeState.collectAsStateWithLifecycle()
            HomeScreen(
                state = uiState,
                onTextChanged = homeViewModel::setFoodText,
                onCalculate = homeViewModel::getCalorie,
                closeSnackBar = homeViewModel::closeSnackBar
            )
        }
    }

}

@Serializable
sealed class ScreenRoutes {

    @Serializable
    data object SplashScreen : ScreenRoutes()

    @Serializable
    data object LoginScreen : ScreenRoutes()

    @Serializable
    data object HomeScreen : ScreenRoutes()
}