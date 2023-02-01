package com.example.android.weather.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.android.weather.R
import com.example.android.weather.domain.weather.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed class WeatherState{
    data class Success(val data: WeatherData, val cityName: String = ""): WeatherState()
    object Loading: WeatherState()
    object Error: WeatherState()
}

@Composable
fun DayWeatherItemDate(
    modifier: Modifier = Modifier,
    time: LocalDate
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if(time.dayOfMonth == LocalDateTime.now().dayOfMonth){
                stringResource(id = R.string.today)
            }else{
                time.format(DateTimeFormatter.ofPattern("EEE")).toString()
            },
            modifier = Modifier.weight(.5f),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "${time.dayOfWeek}/${time.dayOfMonth}"
        )
    }
}
