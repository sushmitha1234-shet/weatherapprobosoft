package com.example.myapplication.response


data class DataFetch (
    val uniqueId: Int,
    val name: String,
    val temperature: String,
    val id: Int,
    val like: Boolean,
    val description: String,
    val min_max: String,
    val precipitation: String,
    val humidity: String,
    val wind: String,
    val visibility: String
)