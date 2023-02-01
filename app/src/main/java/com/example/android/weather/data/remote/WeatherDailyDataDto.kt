package com.example.android.weather.data.remote


import com.squareup.moshi.Json

data class WeatherDailyDataDto(
    @field:Json(name = "sunrise")
    val sunrise: List<String>,
    @field:Json(name = "sunset")
    val sunset: List<String>,
    @field:Json(name = "temperature_2m_max")
    val temperature2mMax: List<Double>,
    @field:Json(name = "temperature_2m_min")
    val temperature2mMin: List<Double>,
    @field:Json(name = "time")
    val time: List<String>,
    @field:Json(name = "weathercode")
    val weatherCode: List<Int>
)