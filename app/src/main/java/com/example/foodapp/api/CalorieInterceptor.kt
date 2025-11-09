package com.example.foodapp.api

import okhttp3.Interceptor
import okhttp3.Response

class CalorieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("x-api-key", "+ldnu+CBKL3PIUKErU5YyA==u8oceFm2eP0shQIl").build()
        val response = chain.proceed(request)
        return response
    }
}