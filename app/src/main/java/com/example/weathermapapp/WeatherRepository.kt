package com.example.weathermapapp

import com.example.weathermapapp.data.WeatherInformation
import retrofit2.Call

class WeatherRepository {

    suspend fun getCurrentWeather(lat : Double, lng : Double, apiKey : String) : Call<WeatherInformation> {
        return WeatherMapApiImpl().api.getCurrentWeather(lat, lng, apiKey)
    }
}