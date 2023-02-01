package com.example.android.weather.data

import android.app.Application
import com.example.android.weather.data.location.DefaultLocationTracker
import com.example.android.weather.data.remote.BASE_URL
import com.example.android.weather.data.remote.WeatherApiService
import com.example.android.weather.data.repository.RemoteWeatherRepository
import com.example.android.weather.domain.location.LocationTracker
import com.example.android.weather.domain.repository.WeatherRepository
import com.google.android.gms.location.LocationServices
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val repository: WeatherRepository
    val locationTracker: LocationTracker
}

class AppDataContainer(
    private val application: Application
): AppContainer {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val retrofitService: WeatherApiService by lazy{
        retrofit.create(WeatherApiService::class.java)
    }

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    override val repository: WeatherRepository by lazy {
        RemoteWeatherRepository(retrofitService)
    }

    override val locationTracker: LocationTracker by lazy {
        DefaultLocationTracker(fusedLocationProviderClient, application)
    }
}