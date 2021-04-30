package com.example.weathermapapp

import androidx.lifecycle.LiveData
import com.example.weathermapapp.data.FavouriteLocations

class LocationRepository(private val locationDao : FavouriteLocationsDAO) {

    var readAllData: LiveData<List<FavouriteLocations>> = locationDao.readAllLocations()

    suspend fun addLocation(location : FavouriteLocations) {
        locationDao.addLocation(location)
    }
}