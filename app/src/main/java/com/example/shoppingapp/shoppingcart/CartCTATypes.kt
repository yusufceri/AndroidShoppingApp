package com.example.shoppingapp.shoppingcart

import com.example.shoppingapp.data.model.Book

sealed class CartCTATypes {
    data class removeFromCart(val book: Book): CartCTATypes()
    class ProceedToCheckout(): CartCTATypes()
    class navigateToCart(): CartCTATypes()
    class navigatetoDashboard(): CartCTATypes()
}
