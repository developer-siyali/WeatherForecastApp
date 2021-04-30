package com.example.weathermapapp

import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastPresenter {
    val typeOfWeatherList = listOf("Cloudy", "Sunny", "Rainy")
    val weatherImages = listOf(R.drawable.forest_cloudy, R.drawable.forest_sunny, R.drawable.forest_rainy)
    val weatherIcons = listOf(R.drawable.partlysunny, R.drawable.clear, R.drawable.rain)
    val colors = listOf(R.color.grey, R.color.sunny, R.color.rainy)

    fun convertKelvinToDegrees(temperature: Double) : Int {
        val degrees = temperature - 273.15
        return degrees.toInt()
    }

    fun getDayOfSearch(dateText : String) : String{
        val sdf = SimpleDateFormat("yyy/MM/dd")
        val formatDate = dateText.split(" ")[0].replace("-", "/")
        val date = sdf.parse(formatDate)
        val sdf2 = SimpleDateFormat("EEEE")
        return sdf2.format(date)
    }

    fun iconWeatherConditionsIndex(weatherCondition : String) : Int {
        return when (weatherCondition) {
            "Clouds" -> 0
            "Sunny" -> 1
            else -> 2
        }
    }
}