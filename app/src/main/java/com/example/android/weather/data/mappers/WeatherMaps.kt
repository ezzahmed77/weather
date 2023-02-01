package com.example.android.weather.data.mappers

import com.example.android.weather.data.remote.WeatherDailyDataDto
import com.example.android.weather.data.remote.WeatherDto
import com.example.android.weather.data.remote.WeatherHourlyDataDto
import com.example.android.weather.domain.weather.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun WeatherInfo.toWeatherData(): WeatherData {
    val currentDayOfMonth: Int = LocalDateTime.now().dayOfMonth
    val dayWeatherMap = this.toDayWeatherMap()
    val todayWeather = dayWeatherMap[currentDayOfMonth]
    return WeatherData(
        dayWeatherMap = dayWeatherMap,
        todayWeather = todayWeather
    )
}

fun WeatherInfo.toDayWeatherMap(): Map<Int, DayWeather> {
    val listOfDailyWeatherData: List<WeatherDailyData> = this.dailyWeatherDataPerDay.values.toList()
    val listOfHourlyWeatherData: List<List<WeatherHourlyData>> = this.dailyWeatherDataPerHour.values.toList()
    val listOfDayWeather: List<DayWeather> = listOfDailyWeatherData.mapIndexed { index, weatherDailyData ->
        DayWeather(
            dailyWeather = weatherDailyData,
            hourlyWeather = listOfHourlyWeatherData[index]
        )
    }
    val dayWeatherMap: Map<Int, DayWeather> = listOfDayWeather.mapIndexed { index, dayWeather ->
        DayWeather(
            dailyWeather = dayWeather.dailyWeather,
            hourlyWeather = dayWeather.hourlyWeather
        )
    }.groupBy {
        it.dailyWeather!!.time.dayOfMonth
    }.mapValues {
        it.value[0]
    }
    return dayWeatherMap

}
fun WeatherHourlyDataDto.toWeatherDayHourlyDataMap(): Map<Int, List<WeatherHourlyData>> {
    return time.mapIndexed{index, time->
        WeatherHourlyData(
            time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
            temperature = temperatures[index],
            dewPoint = dewPoint2m[index],
            relativeHumidity = relativeHumidity2m[index],
            hourlyWeatherType = WeatherType.fromWMO(weatherCode[index]),
            hourlyWindSpeed = windSpeed10m[index],
            hourlyWindDirection = windDirection10m[index],
            hourlyPressure = pressureMsl[index],
            hourlyShortWaveRadiation = shortwaveRadiation[index],
            hourlyVisibility = visibility[index]
        )
    }.groupBy { it.time.dayOfMonth}
}

fun WeatherDailyDataDto.toWeatherDailyDataMap(): Map<Int, WeatherDailyData> {
    return time.mapIndexed{ index, time ->
        WeatherDailyData(
            time = LocalDate.parse(time, DateTimeFormatter.ISO_DATE),
            dailyWeatherType = WeatherType.fromWMO(weatherCode[index]),
            minTemperature = temperature2mMin[index],
            maxTemperature = temperature2mMax[index],
            sunrise = LocalDateTime.parse(sunrise[index], DateTimeFormatter.ISO_DATE_TIME),
            sunset = LocalDateTime.parse(sunset[index], DateTimeFormatter.ISO_DATE_TIME)
        )
    }.groupBy { it.time.dayOfMonth } .mapValues { it.value[0] }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        dailyWeatherDataPerDay = weatherDailyDataDto.toWeatherDailyDataMap(),
        dailyWeatherDataPerHour = weatherHourlyDataDto.toWeatherDayHourlyDataMap()
    )
}

