package com.example.weathermapapp.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_locations", indices = [Index(value = ["cityName","country"], unique = true)])
data class FavouriteLocations(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lat: Double,
    val lng: Double,
    val cityName: String,
    val country : String
)