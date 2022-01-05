package com.example.shoppingapp.domain

import com.example.shoppingapp.data.model.*
import com.example.shoppingapp.vms.ResultStatus

interface Repo {
    suspend fun getAllBooks(): ResultStatus<BookList>
    suspend fun getBook(id: String): ResultStatus<BookItem?>
    suspend fun addToCart(book: BookItem): ResultStatus<Boolean>
    suspend fun removeFromCart(book: BookItem): ResultStatus<Boolean>
    suspend fun getCart(): ResultStatus<List<BookItem>>
    suspend fun getCheckoutConfirmationData(): ResultStatus<CheckoutData>
    suspend fun orderConfirmed(): ResultStatus<OrderConfirmation>
    suspend fun refreshCart(): ResultStatus<Boolean>
    suspend fun loginApp(email: String, password:String): ResultStatus<Boolean>
}