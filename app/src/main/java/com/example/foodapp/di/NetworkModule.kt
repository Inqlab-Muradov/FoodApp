package com.example.foodapp.di

import com.example.foodapp.api.CalorieApiService
import com.example.foodapp.api.CalorieInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideInterceptor(): CalorieInterceptor {
        return CalorieInterceptor()
    }

    @Provides
    fun provideOkHttpClient(interceptor: CalorieInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor = interceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.calorieninjas.com/").client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCalorieApiService(retrofit: Retrofit): CalorieApiService {
        return retrofit.create(CalorieApiService::class.java)
    }

}