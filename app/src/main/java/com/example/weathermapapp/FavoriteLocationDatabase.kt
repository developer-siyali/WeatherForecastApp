package com.example.weathermapapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weathermapapp.data.FavouriteLocations

@Database(entities = [FavouriteLocations::class], version = 1, exportSchema = false)
abstract class FavoriteLocationDatabase : RoomDatabase() {

    abstract fun locationDao() : FavouriteLocationsDAO

    companion object {

        @Volatile
        var INSTANCE: FavoriteLocationDatabase? = null

        fun getDatabase(context: Context): FavoriteLocationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteLocationDatabase::class.java,
                    "favourite_locations"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}