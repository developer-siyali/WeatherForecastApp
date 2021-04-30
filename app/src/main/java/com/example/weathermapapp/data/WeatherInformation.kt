package com.example.weathermapapp.data

import com.google.gson.annotations.SerializedName

data class WeatherInformation(
    @SerializedName("coord")
    val coord: Coordinates,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("base")
    val base : String,
    @SerializedName("main")
    val main: MainWeather,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind : Wind,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("sys")
    val sys: WeatherInfo,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: Int
)