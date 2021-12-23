package com.example.shoppingapp.details.detailsctatypes

import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.home.dashboardctatypes.Dashboardctatypes

sealed class DetailsCTATypes {
    class addToCart(val book: Book): DetailsCTATypes()
    data class buyNow(val book: Book): DetailsCTATypes()
    class navigateToCart(): DetailsCTATypes()
}
