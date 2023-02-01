package com.example.android.weather.domain.weather

data class DayWeather(
    val dailyWeather: WeatherDailyData? = null,
    val hourlyWeather: List<WeatherHourlyData>? = null
)