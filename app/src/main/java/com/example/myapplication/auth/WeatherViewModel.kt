package com.example.myapplication.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.repositories.WeatherRepository
import com.example.myapplication.response.DataFetch
import com.example.myapplication.util.Coroutines

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
    var search: String? = null
    var response = MutableLiveData<DataFetch>()
    fun weatherdeatails(city:String) {
        Coroutines.main {
            val resultresponse = repository.weathersearch(city)
            Log.d("result",resultresponse.toString())
            val temperature = ((resultresponse.main.temp) - 273.15).toInt().toString()
            val min = resultresponse.main.temp_min.minus(273.15).toInt().toString()
            val max = resultresponse.main.temp_max.minus(273.15).toInt().toString()
            val min_max = "$min" + "\u00B0" + "-" + "$max" + "\u00B0"
            val precipitation = resultresponse.clouds.all.toString() + "%"
            val humidity = resultresponse.main.humidity.toString() + "%"
            val wind = resultresponse.wind.speed.toString() + "km/h"
            val visibility = resultresponse.visibility.toString() + "m"
            val id = resultresponse.weather.get(0).id
            val description = resultresponse.weather.get(0).description
            val uniqueId: Int = resultresponse.id
            val name: String = resultresponse.name
            val like = false
            response.value = DataFetch(uniqueId,name,temperature,id,like,description,min_max,precipitation,humidity,wind,visibility)
        }
    }
}