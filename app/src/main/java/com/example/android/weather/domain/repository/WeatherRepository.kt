package com.example.android.weather.domain.repository

import com.example.android.weather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): WeatherInfo
}