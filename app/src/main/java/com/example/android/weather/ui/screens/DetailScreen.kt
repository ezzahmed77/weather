package com.example.android.weather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.weather.R
import com.example.android.weather.domain.utils.getWeatherParametersList
import com.example.android.weather.domain.weather.DayWeather
import com.example.android.weather.domain.weather.WeatherHourlyData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DayWeatherDetailScreen(
    modifier: Modifier = Modifier,
    dayWeather: DayWeather?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        DayWeatherDetailBasic(dayWeather = dayWeather)
        DayWeatherDetailHourInfo(dayWeather = dayWeather)
        DayWeatherDetailParameters(dayWeather = dayWeather)
    }
}

@Composable
fun DayWeatherDetailBasic(
    modifier: Modifier = Modifier,
    dayWeather: DayWeather?
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)){
            Text(
                text = dayWeather?.dailyWeather?.time!!.format(DateTimeFormatter.ofPattern("EEE")),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .alpha(.8f).padding(end = 16.dp)
            )
            Image(
                painter = painterResource(id = dayWeather.dailyWeather.dailyWeatherType.iconRes),
                contentDescription = dayWeather.dailyWeather.dailyWeatherType.weatherDesc,
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 8.dp).weight(.2f)
            )
            Text(
                text = "L: ${dayWeather.dailyWeather.minTemperature.toInt()} \u2103  " +
                        "H: ${dayWeather.dailyWeather.maxTemperature.toInt()} \u2103",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
                    .alpha(.8f)
            )
            Text(
                text = dayWeather.dailyWeather.time.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
                    .alpha(.8f)
            )
        }
    }
}

@Composable
fun DayWeatherDetailHourInfo(
    modifier: Modifier = Modifier,
    dayWeather: DayWeather?
) {
    val text = if(dayWeather?.dailyWeather?.time?.dayOfMonth == LocalDate.now().dayOfMonth){
        "Today"
    }else{
        dayWeather?.dailyWeather?.time?.format(DateTimeFormatter.ofPattern("EEE")).toString()
    }
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colors.onSurface
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ){
                items(dayWeather?.hourlyWeather!!) {item: WeatherHourlyData ->
                    DayWeatherHourInfoItem(item = item)
                }
            }
        }
    }
}
@Composable
fun DayWeatherHourInfoItem(
    modifier: Modifier = Modifier,
    item: WeatherHourlyData
){
    val hourName = if(item.time.hour == LocalDateTime.now().hour){
        "now"
    }else{
        "${item.time.hour} h"
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = hourName,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                painter = painterResource(id = item.hourlyWeatherType.iconRes),
                contentDescription = item.hourlyWeatherType.weatherDesc,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = String.format("%.1f",item.temperature) + " \u2103",
                fontWeight = FontWeight.SemiBold
            )
            DayWeatherHourInfoDetailItem(
                icon = R.drawable.ic_wind,
                value = String.format("%.1f", item.hourlyWindSpeed) + " Km/h"
            )
            DayWeatherHourInfoDetailItem(
                icon = R.drawable.ic_pressure,
                value = String.format("%.1f", item.hourlyPressure.div(1000 )) + " KPa"
            )
            DayWeatherHourInfoDetailItem(
                icon = R.drawable.ic_drop,
                value = item.relativeHumidity.toString() + " %"
            )
            DayWeatherHourInfoDetailItem(
                icon = R.drawable.visibility,
                value = item.hourlyVisibility.div(1000 ).toInt().toString() + " Km"
            )


        }
    }

}


@Composable
fun DayWeatherDetailParameters(
    modifier: Modifier = Modifier,
    dayWeather: DayWeather?
){
    val listOfWeatherParameters = dayWeather?.getWeatherParametersList()
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ){
        DayWeatherDetailItem(
            modifier = Modifier.weight(1f),
            weatherParameter = listOfWeatherParameters?.get(0)!!,
            dayWeather = null,
            onClick = {}
        )
        DayWeatherDetailItem(
            modifier = Modifier.weight(1f),
            weatherParameter = listOfWeatherParameters[1],
            dayWeather = null,
            onClick = {}

        )
    }
}


@Composable
fun DayWeatherHourInfoDetailItem(
    modifier: Modifier = Modifier,
    icon: Int,
    value: String
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = Color.LightGray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            modifier = Modifier
        )
    }
}
