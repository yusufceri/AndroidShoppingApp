package com.example.shoppingapp.data.model

import kotlinx.serialization.*

@Serializable
data class Book (
    val id: Int,
    val title: String,
    val author: String,
    val price: Double,
    val description: String? = null
)