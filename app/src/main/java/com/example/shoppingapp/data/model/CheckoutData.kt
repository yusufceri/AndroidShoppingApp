package com.example.shoppingapp.data.model

data class CheckoutData(
    val shippingAddress: String = "",
    val shippingFee: Double? = 0.0,
    val itemList: List<BookItem>?,
    val estimatedTax: Double?,
    val orderTotal: Double? = 0.0
)
