package com.example.android.weather.data.remote


import com.squareup.moshi.Json

data class WeatherHourlyDataDto(
    @field:Json(name = "dewpoint_2m")
    val dewPoint2m: List<Double>,
    @field:Json(name = "pressure_msl")
    val pressureMsl: List<Double>,
    @field:Json(name = "relativehumidity_2m")
    val relativeHumidity2m: List<Int>,
    @field:Json(name = "shortwave_radiation")
    val shortwaveRadiation: List<Double>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "time")
    val time: List<String>,
    @field:Json(name = "visibility")
    val visibility: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCode: List<Int>,
    @field:Json(name = "winddirection_10m")
    val windDirection10m: List<Int>,
    @field:Json(name = "windspeed_10m")
    val windSpeed10m: List<Double>
)
