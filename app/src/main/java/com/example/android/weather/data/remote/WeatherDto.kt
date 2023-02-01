package com.example.android.weather.data.remote

import com.squareup.moshi.Json


data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherHourlyDataDto: WeatherHourlyDataDto,
    @field:Json(name = "daily")
    val weatherDailyDataDto: WeatherDailyDataDto
)
