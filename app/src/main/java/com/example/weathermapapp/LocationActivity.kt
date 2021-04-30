package com.example.weathermapapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weathermapapp.databinding.LocationInputBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationActivity : AppCompatActivity() {

    private lateinit var binding : LocationInputBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LocationInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeLocationProvider()
        setUpClickListener()
    }


    private fun initializeLocationProvider() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun setUpClickListener() {
        binding.getLocation.setOnClickListener {
            if (getUserLocation() != null) {
                binding.locationNotFound.visibility = View.GONE
                val intent = Intent(this, WeatherForecastActivity::class.java).apply {
                    putExtra("LOCATION", getUserLocation())
                }
                startActivity(intent)
            } else {
                binding.locationNotFound.text = resources.getString(R.string.location_not_found)
                binding.locationNotFound.visibility = View.VISIBLE
            }
        }
    }

    private fun getUserLocation() : String? {
        var coordinates = ""
        return if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            null
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location : Location? ->
                coordinates += "${location?.latitude}  ${location?.longitude}"
            }
            if (coordinates.isEmpty()) null else coordinates
        }
    }
}