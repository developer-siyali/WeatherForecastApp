package com.example.weathermapapp.data.forecast

import com.example.weathermapapp.data.Clouds
import com.example.weathermapapp.data.Weather
import com.example.weathermapapp.data.Wind
import com.google.gson.annotations.SerializedName

data class WeatherForecastList (
        val dt : Int,
        val main : Main,
        val weather: List<Weather>,
        val clouds : Clouds,
        val wind : Wind,
        val snow : Any,
        val sys : SystemForecast,
        @SerializedName("dt_txt")
        val dt_text : String
)