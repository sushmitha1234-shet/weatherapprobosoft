package com.example.myapplication.response

data class ApiResponse (
    val base: String,
    val clouds: Clouds,
    val coord: Coordinates,
    val cod: Int,
    val id: Int,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val weather: List<Weather>,
    val sys: Syc,
    val name: String
)