package com.example.weathermapapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.weathermapapp.FavoriteLocationDatabase
import com.example.weathermapapp.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteLocationViewModel(application: Application): AndroidViewModel(application) {

    var readAllLocations: LiveData<List<FavouriteLocations>>
    private val repository : LocationRepository

    init {
        val locationDAO = FavoriteLocationDatabase.getDatabase(application).locationDao()
        repository = LocationRepository(locationDAO)
        readAllLocations = repository.readAllData
    }

    fun addLocation(location: FavouriteLocations) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLocation(location)
        }
    }
}