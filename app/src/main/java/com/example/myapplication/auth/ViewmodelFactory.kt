package com.example.myapplication.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repositories.WeatherRepository


@Suppress("UNCHECKED_CAST")

class ViewmodelFactory (private val repository: WeatherRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }

}
