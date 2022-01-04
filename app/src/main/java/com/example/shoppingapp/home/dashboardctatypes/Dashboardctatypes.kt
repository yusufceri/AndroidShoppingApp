package com.example.shoppingapp.home.dashboardctatypes

import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.data.model.BookItem

sealed class Dashboardctatypes {
    class navigateToCart(): Dashboardctatypes()
    data class navigateToDetails(val book: BookItem): Dashboardctatypes()
}
