package com.example.weathermapapp.data.forecast

import com.example.weathermapapp.data.Coordinates

data class ForecastCity (
        val id : Int,
        val name : String,
        val coord : Coordinates,
        val country : String
)