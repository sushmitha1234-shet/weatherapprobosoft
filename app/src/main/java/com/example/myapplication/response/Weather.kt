package com.example.myapplication.response

data class Weather (
    val id:Int,
    val main: String,
    val description:String,
    val icon: String
)