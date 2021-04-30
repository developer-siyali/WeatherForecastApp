package com.example.weathermapapp.data

import com.google.gson.annotations.SerializedName

data class MainWeather(
    @SerializedName("temp")
    val temp : Double,
    @SerializedName("pressure")
    val pressure : Int,
    @SerializedName("humidity")
    val humidity : Int,
    @SerializedName("temp_min")
    val temp_min : Double,
    @SerializedName("temp_max")
    val temp_max : Double
)