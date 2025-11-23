package com.example.foodapp.presentation.auth

data class AuthState(
    val usernameLogin: String = "",
    val passwordLogin: String = "",
    val usernameRegister: String = "",
    val passwordRegister: String = "",
    val confirmPasswordRegister: String = "",
    val userEmail: String = ""
)
