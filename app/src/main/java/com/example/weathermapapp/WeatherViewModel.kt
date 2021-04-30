package com.example.weathermapapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermapapp.data.WeatherInformation
import kotlinx.coroutines.launch
import retrofit2.Call

class WeatherViewModel(private val repository : WeatherRepository) : ViewModel() {
    private lateinit var response : Call<WeatherInformation>

    fun getWeatherStatus(lat: Double, lng : Double, apiKey : String) : Call<WeatherInformation> {
        viewModelScope.launch {
            response = repository.getCurrentWeather(lat, lng, apiKey)
        }
        return response
    }
}