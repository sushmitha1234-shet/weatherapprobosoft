package com.example.myapplication.response

import android.content.Context
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyWeatherApi {


    @GET("weather")
    suspend fun searchcity(
        @Query("q")Cityname:String,
        @Query("appid") apikey:String
    ): Response<ApiResponse>


    companion object {
        operator fun invoke():MyWeatherApi{
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create())
                .build().create(MyWeatherApi::class.java)

        }
    }
}