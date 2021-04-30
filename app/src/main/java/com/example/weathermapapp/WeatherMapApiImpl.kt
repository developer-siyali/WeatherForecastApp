package com.example.weathermapapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherMapApiImpl {
    private val baseUrl = "https://api.openweathermap.org/data/2.5/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : WeatherMapApi by lazy {
        retrofit.create(WeatherMapApi::class.java)
    }
}