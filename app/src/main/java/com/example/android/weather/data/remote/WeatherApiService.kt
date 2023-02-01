package com.example.android.weather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.open-meteo.com/"
interface WeatherApiService {
    @GET("v1/forecast?hourly=temperature_2m,relativehumidity_2m,dewpoint_2m,weathercode,pressure_msl,visibility,windspeed_10m,winddirection_10m,shortwave_radiation&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset&current_weather=true&timezone=auto")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}


