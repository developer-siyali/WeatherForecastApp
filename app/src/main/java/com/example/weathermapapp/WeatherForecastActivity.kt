package com.example.weathermapapp

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.setPadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.weathermapapp.data.FavouriteLocationViewModel
import com.example.weathermapapp.data.FavouriteLocations
import com.example.weathermapapp.data.WeatherInformation
import com.example.weathermapapp.data.forecast.WeatherForecast
import com.example.weathermapapp.databinding.DrawerToolBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WeatherForecastActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private val currentWeatherPresenter = WeatherForecastPresenter()
    private lateinit var nav: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var bindingDrawerTool: DrawerToolBinding
    val degree =  '\u00B0'
    var weatherAttributesIndex : Int = -1

    private lateinit var viewModel: FavouriteLocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDrawerTool = DrawerToolBinding.inflate(layoutInflater)
        setContentView(bindingDrawerTool.root)


        val message = intent.getStringExtra("LOCATION")
        val coord = message?.split(" ")
        viewModel = ViewModelProvider(this).get(FavouriteLocationViewModel::class.java)
        toolbar = bindingDrawerTool.toolbar
        drawerLayout = bindingDrawerTool.drawer
        nav = bindingDrawerTool.navMenu

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.closed)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        val repository = WeatherRepository()
        val viewModelFactory = WeatherViewModelFactory(repository)
        weatherViewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        val response =   coord?.let {  weatherViewModel.getWeatherStatus(coord[0].toDouble(), coord[1].toDouble(), "d47906c68ca88dcf938e774be2a84ee1") }
        response?.enqueue(object : Callback<WeatherInformation> {
            override fun onFailure(call: Call<WeatherInformation>, t: Throwable) {

                Log.i("ViewCurrentWeather", t.cause.toString())
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onResponse(
                call: Call<WeatherInformation>,
                response: Response<WeatherInformation>
            ) {
                if (response.code() == 200) {
                    val currentWeatherResponse = response.body()
                    val weatherDescription = currentWeatherResponse?.weather?.get(0)?.main
                    weatherAttributesIndex = weatherDescription?.let { currentWeatherPresenter.iconWeatherConditionsIndex(it) } ?: -1
                    val weatherIcon = currentWeatherPresenter.weatherImages[weatherAttributesIndex]
                    val current = currentWeatherPresenter.colors[weatherAttributesIndex]
                    val weatherDescriptionElement = currentWeatherPresenter.typeOfWeatherList[weatherAttributesIndex]

                    bindingDrawerTool.weatherDescription.text = weatherDescriptionElement

                    bindingDrawerTool.iconWeather.setBackgroundResource(weatherIcon)
                    bindingDrawerTool.view.setBackgroundColor(resources.getColor(current, null))
                    bindingDrawerTool.tempDetails.setBackgroundColor(resources.getColor(current, null))
                    bindingDrawerTool.relativeLayout.setBackgroundColor(resources.getColor(current, null))

                    bindingDrawerTool.currentTemp.text =
                        response.body()?.main?.temp?.let {
                            currentWeatherPresenter.convertKelvinToDegrees(it).toString() + degree
                        }
                    bindingDrawerTool.currentTempNumber.text = response.body()?.main?.temp?.let {
                        currentWeatherPresenter.convertKelvinToDegrees(it).toString() + degree
                    }
                    bindingDrawerTool.minTempNumber.text = response.body()?.main?.temp_min?.let {
                        currentWeatherPresenter.convertKelvinToDegrees(it).toString() + degree
                    }
                    bindingDrawerTool.maxTempNumber.text = response.body()?.main?.temp_max?.let {
                        currentWeatherPresenter.convertKelvinToDegrees(it).toString() + degree
                    }
                } else {
                    Log.i("ViewCurrentWeather", "error with connection")
                }
            }

        })

        val forecastResponse = coord?.let {  WeatherMapApiImpl().api.getWeatherForecast(coord[0].toDouble(), coord[1].toDouble(), "d47906c68ca88dcf938e774be2a84ee1") }
        forecastResponse?.enqueue(object : Callback<WeatherForecast> {
            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                Log.i("ViewCurrentWeather", t.cause.toString())
            }

            override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {

                if (response.code() == 200) {
                    val forecastTable = bindingDrawerTool.tableOfDays
                    var count  = 0
                    val responseAttributes = response.body()

                    while (count < 30) {
                        val tableRow = TableRow(this@WeatherForecastActivity)
                        tableRow.layoutParams =
                            TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT
                            )
                        val dayText = TextView(this@WeatherForecastActivity)
                        dayText.text = responseAttributes?.list?.get(count)?.dt_text?.let {
                            currentWeatherPresenter.getDayOfSearch(
                                it
                            )
                        }
                        dayText.textSize = 23F
                        tableRow.addView(dayText, TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f))
                        val iconImage = ImageView(this@WeatherForecastActivity)
                        iconImage.setImageResource(currentWeatherPresenter.weatherIcons[weatherAttributesIndex])
                        iconImage.minimumHeight = 100
                        iconImage.minimumWidth = 100
                        tableRow.addView(iconImage, TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f))
                        val temperatureText = TextView(this@WeatherForecastActivity)
                        val mainTemp = responseAttributes?.list?.get(count)?.main?.temp
                        temperatureText.text =
                            mainTemp?.let { currentWeatherPresenter.convertKelvinToDegrees(it).toString().trim() + degree}
                        temperatureText.textSize = 23F
                        temperatureText.gravity = Gravity.RIGHT
                        tableRow.addView(temperatureText, TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f))
                        tableRow.setPadding(30)
                        forecastTable.addView(tableRow)
                        count += 7
                    }


                } else {
                    Log.i("ViewCurrentWeather", "error with connection")
                }
            }
        })

        nav.setNavigationItemSelectedListener( NavigationView.OnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.add_location -> {
                    if (coord != null) insertDataToDatabase(coord[0].toDouble(), coord[1].toDouble())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    Toast.makeText(this, "Location successfully added", Toast.LENGTH_LONG).show()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.view_favourite_locations -> {
                    bindingDrawerTool.toolbarLinearLayout.visibility = View.GONE
                    bindingDrawerTool.tempLinearLayout.visibility = View.GONE
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, FavouriteLocationList())
                    ft.commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    private fun insertDataToDatabase(lat: Double, lng: Double) {

        val gcd = Geocoder(applicationContext, Locale.getDefault())
        val addresses = gcd.getFromLocation(lat, lng, 1)
        var cityName = ""
        var country = ""
        if (addresses.size > 0) {
            cityName = addresses[0].subAdminArea
            country = addresses[0].countryName
            val location = FavouriteLocations(0, lat = lat, lng = lng, cityName = cityName, country = country)
            viewModel.addLocation(location)
        }
    }
}
