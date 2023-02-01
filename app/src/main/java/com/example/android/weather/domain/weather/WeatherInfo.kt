package com.example.android.weather.domain.weather

data class WeatherInfo(
    val dailyWeatherDataPerDay: Map<Int, WeatherDailyData>,
    // Remember to get the current hour
    val dailyWeatherDataPerHour: Map<Int, List<WeatherHourlyData>>
)
