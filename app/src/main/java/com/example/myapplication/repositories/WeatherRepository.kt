package com.example.myapplication.repositories

import com.example.myapplication.response.ApiResponse
import com.example.myapplication.response.MyWeatherApi
import com.example.myapplication.response.WeatherResponseConverter

class WeatherRepository(private val Api : MyWeatherApi) : WeatherResponseConverter() {
    suspend fun weathersearch(search: String) : ApiResponse {
        val api = "6b2dd06a6664d5536aeb9458636308e9"

        var searchResponse : ApiResponse
        return apiRequest { Api.searchcity(search, api) }
    }
}



