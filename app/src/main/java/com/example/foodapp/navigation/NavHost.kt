package com.example.foodapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.foodapp.presentation.home.HomeScreen
import com.example.foodapp.presentation.home.HomeViewModel
import com.example.foodapp.presentation.auth.AuthScreen
import com.example.foodapp.presentation.auth.AuthViewModel
import com.example.foodapp.presentation.splash.SplashScreen
import com.example.foodapp.presentation.splash.SplashViewModel
import kotlinx.serialization.Serializable

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SplashScreen,
    ) {
        composable<ScreenRoutes.SplashScreen> {
            val splashViewModel: SplashViewModel = hiltViewModel()
            val email by splashViewModel.email.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                splashViewModel.isLogin.collect {
                    if (it) navController.navigate(ScreenRoutes.HomeScreen(email)){
                        popUpTo(ScreenRoutes.SplashScreen){
                            inclusive = true
                        }
                    }
                }
            }
            SplashScreen(
                navigateToLogin = {
                    navController.navigate(ScreenRoutes.LoginScreen) {
                        popUpTo(ScreenRoutes.SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<ScreenRoutes.LoginScreen> {
            val authViewModel: AuthViewModel = hiltViewModel()
            val state by authViewModel.authState.collectAsStateWithLifecycle()
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                authViewModel.errorMessage.collect { msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                }
            }
            LaunchedEffect(Unit) {
                authViewModel.successfulAuth.collect {
                    if (it) navController.navigate(ScreenRoutes.HomeScreen(userEmail = state.userEmail)) {
                        popUpTo(ScreenRoutes.LoginScreen) {
                            inclusive = true
                        }
                    }
                }
            }
            AuthScreen(
                state = state,
                setNewText = authViewModel::setNewText,
                login = authViewModel::login,
                register = authViewModel::register
            )
        }

        composable<ScreenRoutes.HomeScreen> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val uiState by homeViewModel.homeState.collectAsStateWithLifecycle()
            val userEmail = it.toRoute<ScreenRoutes.HomeScreen>().userEmail
            HomeScreen(
                state = uiState,
                onTextChanged = homeViewModel::setFoodText,
                onCalculate = homeViewModel::getCalorie,
                closeSnackBar = homeViewModel::closeSnackBar,
                userEmail = userEmail
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
    data class HomeScreen(
        val userEmail: String
    ) : ScreenRoutes()
}