package com.example.android.weather.ui.screens

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.weather.data.mappers.toWeatherData
import com.example.android.weather.domain.location.LocationTracker
import com.example.android.weather.domain.repository.WeatherRepository
import com.example.android.weather.domain.weather.DayWeather
import com.example.android.weather.domain.weather.WeatherInfo
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*


class HomeScreenViewModel(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    context: Context
    ): ViewModel() {

    var weatherState: WeatherState by mutableStateOf(WeatherState.Loading)
    private set
    // Get City Name
    private val geocoder = Geocoder(context, Locale.getDefault())
    // Current Detail DayWeather
    var currentDayWeather: DayWeather by mutableStateOf(DayWeather())
    private set


    fun loadWeatherData(){
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let {location->
                val result: WeatherInfo = repository.getWeatherData(location.latitude, location.longitude)
                val resultWeatherData = result.toWeatherData()
                val addresses: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val cityName: String = addresses!![0].locality
                weatherState = WeatherState.Success(data = resultWeatherData, cityName = cityName)
                currentDayWeather = resultWeatherData.todayWeather!!

            } ?: kotlin.run {
                weatherState = WeatherState.Error
            }
        }
    }

    fun updateCurrentDayWeather(dayWeather: DayWeather) {
        currentDayWeather = dayWeather
    }
}