package com.example.shoppingapp.data.model

import java.io.Serializable

data class Book (
    val id: Int,
    val title: String,
    val author: String,
    val price: Double,
    val description: String? = null
): Serializable