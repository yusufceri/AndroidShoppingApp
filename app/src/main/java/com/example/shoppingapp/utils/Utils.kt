package com.example.shoppingapp.utils

import java.math.RoundingMode
import java.text.DecimalFormat

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

fun Double.roundOffDecimal(): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}
