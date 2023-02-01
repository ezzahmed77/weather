package com.example.android.weather.domain.weather

data class WeatherData(
    val dayWeatherMap: Map<Int, DayWeather>,
    val todayWeather: DayWeather?
)
