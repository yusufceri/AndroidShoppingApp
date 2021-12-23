package com.example.shoppingapp.checkout.checkoutctatypes

import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.details.detailsctatypes.DetailsCTATypes

sealed class CheckoutCTATypes {
    class placeOrder(): CheckoutCTATypes()
    class navigateToDashboard(): CheckoutCTATypes()
}
