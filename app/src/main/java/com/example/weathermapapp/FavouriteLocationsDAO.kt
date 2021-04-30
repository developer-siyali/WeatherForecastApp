package com.example.weathermapapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathermapapp.data.FavouriteLocations

@Dao
interface FavouriteLocationsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(location: FavouriteLocations)

    @Query("SELECT * FROM favourite_locations ORDER BY id ASC")
    fun readAllLocations(): LiveData<List<FavouriteLocations>>
}