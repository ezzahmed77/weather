package com.example.android.weather.domain.weather

import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherDailyData(
    val time: LocalDate,
    val dailyWeatherType: WeatherType,
    val minTemperature: Double,
    val maxTemperature: Double,
    val sunset: LocalDateTime,
    val sunrise: LocalDateTime
)
