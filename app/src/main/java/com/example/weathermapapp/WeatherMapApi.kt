package com.example.weathermapapp

import com.example.weathermapapp.data.WeatherInformation
import com.example.weathermapapp.data.forecast.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherMapApi {

    @GET("weather?")
    fun getCurrentWeather(@Query("lat") lat : Double,  @Query("lon") lng : Double, @Query("appid") apiKey : String) : Call<WeatherInformation>

    @GET("forecast?")
    fun getWeatherForecast(@Query("lat") lat : Double,  @Query("lon") lng : Double, @Query("appid") apiKey : String) : Call<WeatherForecast>
}