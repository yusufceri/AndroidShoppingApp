package com.example.shoppingapp.utils

enum class CurrencyType {
    USD
}

fun getPrice(currencyType: String?, price: String): String {
    when(currencyType) {
        CurrencyType.USD.name ->
            return "$"+price
    }
    return price+" USD"
}