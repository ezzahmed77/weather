package com.example.android.weather.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android.weather.R
import com.example.android.weather.domain.utils.WeatherParameter
import com.example.android.weather.domain.utils.getCurrentWeatherHourlyData
import com.example.android.weather.domain.utils.getWeatherParametersList
import com.example.android.weather.domain.weather.DayWeather
import com.example.android.weather.domain.weather.WeatherData
import com.example.android.weather.domain.weather.WeatherHourlyData
import com.example.android.weather.ui.navigation.Screen
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherData: WeatherData?,
    cityName: String,
    navController: NavHostController,
    viewModel: HomeScreenViewModel,
) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier
        ) {
            composable(
                route = Screen.Home.route
            ){
                HomeScreenContent(
                    weatherData = weatherData,
                    cityName = cityName,
                    navController = navController,
                    updateCurrentDayWeather = { viewModel.updateCurrentDayWeather(it) }
                )
            }
            composable(
                route = Screen.Detail.route
            ) {
                DayWeatherDetailScreen(dayWeather = viewModel.currentDayWeather)
            }
        }

}



@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    weatherData: WeatherData?,
    cityName: String,
    navController: NavHostController,
    updateCurrentDayWeather: (DayWeather) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {
        WeatherCard(dayWeather = weatherData?.todayWeather, cityName = cityName)
        TodayWeatherHours(todayWeather = weatherData?.todayWeather, onClick = {
            updateCurrentDayWeather(it)
            navController.navigate(Screen.Detail.route)
        } )
        ForecastWeatherData(
            dayWeatherList = weatherData?.dayWeatherMap?.values?.toList()!!,
            onClick = {
                updateCurrentDayWeather(it)
                navController.navigate(Screen.Detail.route)
            }
        )
        DayWeatherDetail(
            dayWeather = weatherData.todayWeather,
            onClick = {
                updateCurrentDayWeather(it)
                navController.navigate(Screen.Detail.route)
            }
        )
    }
}

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    dayWeather: DayWeather?,
    cityName: String
){
    val currentHourData = dayWeather?.getCurrentWeatherHourlyData()
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 16.dp,
        backgroundColor = MaterialTheme.colors.surface
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, end = 8.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Today : ${currentHourData?.time?.hour}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = cityName,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal
            )
            Image(
                painter = painterResource(id = currentHourData?.hourlyWeatherType?.iconRes!!),
                contentDescription = currentHourData.hourlyWeatherType.weatherDesc,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = String.format("%.1f", currentHourData.temperature) + " \u2103",
                fontSize = 48.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = currentHourData.hourlyWeatherType.weatherDesc,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp
            )
            // showing min & max temp of day
            Row(
                modifier = Modifier.padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Text(
                    text = "L: ${String.format("%.1f", dayWeather.dailyWeather!!.minTemperature)} ℃",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "H: ${String.format("%.1f", dayWeather.dailyWeather.maxTemperature)} ℃",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun TodayWeatherHours(
    modifier: Modifier = Modifier,
    todayWeather: DayWeather?,
    onClick: (DayWeather) -> Unit,
) {
    Card(
        modifier = modifier.clickable { onClick(todayWeather!!) },
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,

    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Today",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ){
                items(todayWeather?.hourlyWeather!!) {item: WeatherHourlyData ->
                    HourlyWeatherItem(weatherHourlyDataItem = item)
                }
            }

        }
    }

}

@Composable
fun HourlyWeatherItem(
    modifier: Modifier = Modifier,
    weatherHourlyDataItem: WeatherHourlyData?
) {
    val hourName = if(weatherHourlyDataItem?.time?.hour == LocalDateTime.now().hour){
        "now"
    }else{
        "${weatherHourlyDataItem?.time?.hour} h"
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
                painter = painterResource(id = weatherHourlyDataItem?.hourlyWeatherType?.iconRes!!),
                contentDescription = weatherHourlyDataItem.hourlyWeatherType.weatherDesc,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = String.format("%.1f",weatherHourlyDataItem.temperature) + " \u2103",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ForecastWeatherData(
    modifier: Modifier = Modifier,
    dayWeatherList: List<DayWeather>,
    onClick: (DayWeather) -> Unit
){
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.forecast),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(250.dp)
            ) {
                items(dayWeatherList){dayWeather->
                    DayWeatherItem(dayWeatherItem = dayWeather, onClick = { onClick(dayWeather)})
                }
            }
        }
    }
}

@Composable
fun DayWeatherItem(
    modifier: Modifier = Modifier,
    dayWeatherItem: DayWeather,
    onClick: (DayWeather) -> Unit,
){
    Card(
        modifier = modifier.clickable { onClick(dayWeatherItem) },
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.7f)
                    .wrapContentSize(Alignment.Center)
            ){

                Text(
                    text = if(dayWeatherItem.dailyWeather!!.time.dayOfMonth == LocalDate.now().dayOfMonth){
                        stringResource(id = R.string.today)
                    }else{
                        dayWeatherItem.dailyWeather.time.format(DateTimeFormatter.ofPattern("EEE")).toString()
                    },
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = dayWeatherItem.dailyWeather.time.format(DateTimeFormatter.ofPattern("dd/MM"))
                )
            }

            Image(
                painter = painterResource(id = dayWeatherItem.dailyWeather!!.dailyWeatherType.iconRes),
                contentDescription = dayWeatherItem.dailyWeather.dailyWeatherType.weatherDesc,
                modifier = Modifier
                    .size(20.dp)
                    .weight(1.5f)
            )

            Text(
                text = dayWeatherItem.dailyWeather.minTemperature.toInt().toString(),
                modifier = Modifier
                    .alpha(.7f)
                    .weight(.5f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Text(
                text = dayWeatherItem.dailyWeather.maxTemperature.toInt().toString(),
                modifier = Modifier
                    .alpha(1f)
                    .weight(.5f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

        }
    }
}

@Composable
fun DayWeatherDetail(
    modifier: Modifier = Modifier,
    dayWeather: DayWeather?,
    onClick: (DayWeather) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.height(550.dp)
    ){
        items(dayWeather?.getWeatherParametersList()!!){
            DayWeatherDetailItem(dayWeather = dayWeather, weatherParameter = it, onClick = { onClick(it) })
        }
    }
}

@Composable
fun DayWeatherDetailItem(
    modifier: Modifier = Modifier,
    weatherParameter: WeatherParameter,
    dayWeather: DayWeather?,
    onClick: (DayWeather) -> Unit
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable { onClick(dayWeather!!) },
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.secondary
    ){
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = weatherParameter.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                        .alpha(.8f),
                    tint = Color.White
                )
                Text(
                    text = weatherParameter.title,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.alpha(.7f),
                    color = MaterialTheme.colors.onSurface
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .weight(1f)
                    .fillMaxWidth()

            ){
                Text(
                    text = weatherParameter.value,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface
                )
                weatherParameter.unit?.let {
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}


@Composable
fun ErrorAndLoadingScreen(
    modifier: Modifier = Modifier,
    weatherState: WeatherState
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ){
        Text(
            text = if(weatherState == WeatherState.Loading){
                stringResource(id = R.string.loading)
            }else{
                stringResource(id = R.string.error)
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}