package com.example.foodapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.api.CalorieApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val calorieApiService: CalorieApiService
): ViewModel(){

    init {
        getCalorie()
    }

    private fun getCalorie(){
        viewModelScope.launch {
            delay(3000)
           val result =  calorieApiService.getCalorie("rice")
            if (result.isSuccessful){
                Log.d("Success","succcess")
            }
        }
    }

}
