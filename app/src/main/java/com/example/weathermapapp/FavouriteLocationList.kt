package com.example.weathermapapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermapapp.data.FavouriteLocationViewModel
import com.example.weathermapapp.databinding.RecyclerViewBinding

class FavouriteLocationList : Fragment(), FavouritesListAdapter.OnItemClickListener{

    private lateinit var locationViewModel: FavouriteLocationViewModel
    private lateinit var binding: RecyclerViewBinding
    private var weatherMap = WeatherMapLocations()
    private val adapter = FavouritesListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecyclerViewBinding.inflate(layoutInflater, container, false)

        val recyclerView = binding.favouriteLocations
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        locationViewModel = ViewModelProvider(this).get(FavouriteLocationViewModel::class.java)
        locationViewModel.readAllLocations.observe(viewLifecycleOwner, Observer { locations ->
            adapter.setData(locations = locations)
        })
        return binding.root
    }

    override fun onItemClick(position: Int) {

        val dialog = context?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(true)
        dialog?.setTitle("Choose to view selected Location in Map or the Weather Screen")

        dialog?.setNegativeButton("Map", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
            locationViewModel.readAllLocations.observe(viewLifecycleOwner, Observer { locations ->
                val locationInstance = locations[position]
                val intent = Intent(activity, WeatherMapLocations::class.java).apply {
                    putExtra("LOCATION", "${locationInstance.lat} ${locationInstance.lng}")
                }
                startActivity(intent)
            })
            dialogInterface.dismiss()

        })

        dialog?.setPositiveButton("Weather Forecast", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
            locationViewModel.readAllLocations.observe(viewLifecycleOwner, Observer { locations ->
                val locationInstance = locations[position]
                val intent = Intent(activity, WeatherForecastActivity::class.java).apply {
                    putExtra("LOCATION", "${locationInstance.lat} ${locationInstance.lng}")
                    putExtra("LOCATION FLAG", "0")
                }
                startActivity(intent)
            })
            dialogInterface.dismiss()
        })
        dialog?.show()
    }
}