package com.example.myapplication.response

import com.example.myapplication.HomeActivity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyWeatherApi {
    @GET("weather?")
    suspend fun searchcity(
        @retrofit2.http.Query("q") Cityname: String,
        @retrofit2.http.Query("appid") apiKey: String
    ) : Response<ApiResponse>

    companion object{
        operator fun  MyWeatherApi {
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(
                    GsonConverterFactory.create())
                .build().create(MyWeatherApi::class.java)
        }
    }
}
