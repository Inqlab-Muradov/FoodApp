package com.example.foodapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.api.CalorieApiService
import com.example.foodapp.data.CalorieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calorieApiService: CalorieApiService
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    fun setFoodText(text: String, index: Int) {
        _homeState.value = when (index) {
            0 -> _homeState.value.copy(breakfastValue = text)
            1 -> _homeState.value.copy(lunchValue = text)
            2 -> _homeState.value.copy(saladValue = text)
            3 -> _homeState.value.copy(dinnerValue = text)
            else -> _homeState.value
        }
    }

    fun getCalorie() {
        _homeState.value = _homeState.value.copy(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val query = with(_homeState.value) {
                "$breakfastValue $lunchValue $saladValue $dinnerValue"
            }
            try {
                val response = calorieApiService.getCalorie(query)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        if (response.body()?.items.isNullOrEmpty()) {
                            _homeState.value = _homeState.value.copy(isEmptyMessage = true)
                        }
                        updateUiState(response)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        val error = response.errorBody().toString()
                        _homeState.value = _homeState.value.copy(error = error)
                    }
                }
            } catch (e: Exception) {
                _homeState.value = _homeState.value.copy(error = e.message)
            } finally {
                _homeState.value = _homeState.value.copy(loading = false)
            }
        }
    }

    private fun updateUiState(response: Response<CalorieResponse>) {
        val calorie = response.body()?.items?.map { item ->
            item?.calories
        }?.fold(0.0) { acc, calorie ->
            acc + (calorie ?: 0.0)
        }
        val caloriePercentage = calorie?.let {
            it / 2000
        }?.coerceAtMost(1.00)
        val carbohydrates = response.body()?.items?.map { item ->
            item?.carbohydrates_total_g
        }?.fold(0.0) { acc, carbohydrates ->
            acc + (carbohydrates ?: 0.0)
        }
        val carboHydratePercentage = carbohydrates?.let {
            it / 256
        }

        val proteins = response.body()?.items?.map { item ->
            item?.protein_g
        }?.fold(0.0) { acc, protein ->
            acc + (protein ?: 0.0)
        }
        val proteinPercentage = proteins?.let {
            it / 256
        }

        val fat = response.body()?.items?.map { item ->
            item?.fat_total_g
        }?.fold(0.0) { acc, fat ->
            acc + (fat ?: 0.0)
        }
        val fatPercentage = fat?.let {
            it / 256
        }
        val updatedList = _homeState.value.calorieItemList.toMutableList()

        updatedList[0] = updatedList[0].copy(
            amount = carbohydrates ?: 0.0,
            percentage = carboHydratePercentage?.toFloat()
        )
        updatedList[1] = updatedList[1].copy(
            amount = proteins ?: 0.0,
            percentage = proteinPercentage?.toFloat()
        )
        updatedList[2] = updatedList[2].copy(
            amount = fat ?: 0.0,
            percentage = fatPercentage?.toFloat()
        )
        _homeState.value = _homeState.value.copy(
            calorie = calorie,
            initialCaloriePercentage = caloriePercentage?.toFloat(),
            calorieItemList = updatedList
        )
    }

    fun closeSnackBar() {
        _homeState.value = _homeState.value.copy(isEmptyMessage = false)
    }
}
