package com.example.weathermapapp.data.forecast

import com.google.gson.annotations.SerializedName

data class WeatherForecast (
        @SerializedName("cod")
        val cod  : String,
        @SerializedName("message")
        val message : Double,
        @SerializedName("cnt")
        val cnt : Int,
        @SerializedName("list")
        val list : List<WeatherForecastList>,
        @SerializedName("city")
        val city : ForecastCity
)