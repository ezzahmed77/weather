package com.example.android.weather.domain.weather

import java.time.LocalDateTime

data class WeatherHourlyData(
    val time: LocalDateTime,
    val temperature: Double,
    val dewPoint: Double,
    val relativeHumidity: Int,
    val hourlyWeatherType: WeatherType,
    val hourlyWindSpeed: Double,
    val hourlyWindDirection: Int,
    val hourlyPressure: Double,
    val hourlyShortWaveRadiation: Double,
    val hourlyVisibility: Double
)

