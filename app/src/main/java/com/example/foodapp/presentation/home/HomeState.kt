package com.example.foodapp.presentation.home

import androidx.compose.ui.graphics.Color


data class HomeState(
    var breakfastValue: String = "",
    var lunchValue: String = "",
    var saladValue: String = "",
    var dinnerValue: String = "",
    val calorieItemList: List<CalorieItemData> = listOf(
        CalorieItemData(
            title = "Carbs",
            percentage = 0.00f,
            amount = 0.0,
            progressColor = Color(0xFF4A90E2)
        ),
        CalorieItemData(
            title = "Proteins",
            percentage = 0.00f,
            amount = 0.0,
            progressColor = Color(0xFF9B51E0)
        ),
        CalorieItemData(
            title = "Fat",
            percentage = 0.00f,
            amount = 0.0,
            progressColor = Color(0xFFF5A623)
        ),
    ),
    var calorie: Double? = 0.0,
    var carbohydrates: Double? = 0.0,
    var protein: Double? = 0.0,
    var fat: Double? = 0.0,
    var initialCaloriePercentage: Float? = 0.00f,
    var error: String? = null,
    var loading: Boolean = false,
    var isEmptyMessage: Boolean = false
)

data class CalorieItemData(
    val title: String,
    val amount: Double,
    var percentage: Float?,
    val progressColor: Color
)