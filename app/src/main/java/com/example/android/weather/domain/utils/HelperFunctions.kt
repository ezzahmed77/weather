package com.example.android.weather.domain.utils

import com.example.android.weather.R
import com.example.android.weather.domain.weather.DayWeather
import com.example.android.weather.domain.weather.WeatherHourlyData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun DayWeather.getCurrentWeatherHourlyData(): WeatherHourlyData? {
    return this.hourlyWeather!!.find {
        it.time.hour == LocalDateTime.now().hour
    }
}

fun DayWeather.getWeatherParametersList(): List<WeatherParameter> {
    return listOf<WeatherParameter>(
        WeatherParameter(
            icon = R.drawable.sunrise,
            title = "Sunrise",
            value = this.dailyWeather!!.sunrise.format(DateTimeFormatter.ofPattern("hh:mm")),
        ),
        WeatherParameter(
            icon = R.drawable.sunset,
            title = "Sunset",
            value = this.dailyWeather.sunset.format(DateTimeFormatter.ofPattern("hh:mm")),
        ),
        WeatherParameter(
            icon = R.drawable.ic_drop,
            title = "Humidity",
            value = (this.getCurrentWeatherHourlyData()?.relativeHumidity).toString() + " %"
        ),

        WeatherParameter(
            icon = R.drawable.dew_point,
            title = "Dew",
            value = String.format("%.1f", this.getCurrentWeatherHourlyData()?.dewPoint) + " \u2103",
        ),
        WeatherParameter(
            icon = R.drawable.ic_wind,
            title = "Wind",
            value = this.getCurrentWeatherHourlyData()?.hourlyWindSpeed.toString(),
            unit = "km/h"
        ),
        WeatherParameter(
            icon = R.drawable.ic_pressure,
            title = "Pressure",
            value = String.format("%.2f", this.getCurrentWeatherHourlyData()?.hourlyPressure?.div(1000)),
            unit = "kPa"
        ),

        WeatherParameter(
            icon = R.drawable.visibility,
            title = "Visibility",
            value = String.format("%.1f", this.getCurrentWeatherHourlyData()?.hourlyVisibility?.div(1000)),
            unit = "Km"
        ),
        WeatherParameter(
            icon = R.drawable.sunrise,
            title = "Short wave",
            value = String.format("%.1f", this.getCurrentWeatherHourlyData()?.hourlyShortWaveRadiation),
            unit = "W/m²"
        ),

    )
}
data class WeatherParameter(
    val icon: Int,
    val title: String,
    val value: String,
    val unit: String? = null
)