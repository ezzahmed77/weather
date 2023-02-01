package com.example.android.weather.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android.weather.WeatherApplication
import com.example.android.weather.ui.screens.HomeScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            HomeScreenViewModel(
                weatherApplication().container.repository,
                weatherApplication().container.locationTracker,
                weatherApplication().applicationContext
            )
        }
    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [WeatherApplication].
 */
fun CreationExtras.weatherApplication(): WeatherApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeatherApplication)
