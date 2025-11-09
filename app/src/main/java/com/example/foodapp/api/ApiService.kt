package com.example.foodapp.api

import com.example.foodapp.data.CalorieResponse
import com.example.foodapp.data.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CalorieApiService {
    @GET("v1/nutrition")
    suspend fun getCalorie(@Query("query") name: String): Response<CalorieResponse>
}