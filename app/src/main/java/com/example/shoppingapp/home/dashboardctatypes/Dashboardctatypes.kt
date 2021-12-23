package com.example.shoppingapp.home.dashboardctatypes

import com.example.shoppingapp.data.model.Book

sealed class Dashboardctatypes {
    class navigateToCart(): Dashboardctatypes()
    data class navigateToDetails(val book: Book): Dashboardctatypes()
}
