package com.example.shoppingapp.details.detailsctatypes

import com.example.shoppingapp.data.model.BookItem

sealed class DetailsCTATypes {
    class addToCart(val book: BookItem): DetailsCTATypes()
    data class buyNow(val book: BookItem): DetailsCTATypes()
    class navigateToCart(): DetailsCTATypes()
}
