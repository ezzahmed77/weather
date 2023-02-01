package com.example.android.weather.data.repository

import com.example.android.weather.data.mappers.toWeatherInfo
import com.example.android.weather.data.remote.WeatherApiService
import com.example.android.weather.domain.repository.WeatherRepository
import com.example.android.weather.domain.weather.WeatherInfo

class RemoteWeatherRepository(private val service: WeatherApiService): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): WeatherInfo {
        return service.getWeatherData(lat, long).toWeatherInfo()
    }
}