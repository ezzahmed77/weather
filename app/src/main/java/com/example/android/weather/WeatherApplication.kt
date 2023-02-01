package com.example.android.weather

import android.app.Application
import com.example.android.weather.data.AppContainer
import com.example.android.weather.data.AppDataContainer

class WeatherApplication(): Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer( this)
    }
}