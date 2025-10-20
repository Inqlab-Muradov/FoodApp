package com.example.foodapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import com.example.foodapp.navigation.MainNavHost
import com.example.foodapp.presentation.splash.SplashScreen
import com.example.foodapp.ui.theme.FoodAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodAppTheme {
                MainNavHost()
            }
        }
    }
}