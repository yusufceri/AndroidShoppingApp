package com.example.shoppingapp.domain

import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.data.model.CheckoutData
import com.example.shoppingapp.data.model.OrderConfirmation
import com.example.shoppingapp.vms.ResultStatus

interface Repo {
    suspend fun getAllBooks(): ResultStatus<List<Book>>
    suspend fun getBook(id: Int): ResultStatus<Book?>
    suspend fun addToCart(book: Book): ResultStatus<Boolean>
    suspend fun removeFromCart(book: Book): ResultStatus<Boolean>
    suspend fun getCart(): ResultStatus<List<Book>>
    suspend fun getCheckoutConfirmationData(): ResultStatus<CheckoutData>
    suspend fun orderConfirmed(): ResultStatus<OrderConfirmation>
    suspend fun refreshCart(): ResultStatus<Boolean>
}