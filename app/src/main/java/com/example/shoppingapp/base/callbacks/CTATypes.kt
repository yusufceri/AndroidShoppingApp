package com.example.shoppingapp.base.callbacks

import com.example.shoppingapp.data.model.Book

interface  CTATypes {
    fun navigateToCart()
    fun navigateToDetails(book: Book)
}
