package com.example.shoppingapp.domain

import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.data.model.*
import com.example.shoppingapp.vms.ResultStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getAllBooks(): ResultStatus<BookList>  = withContext(Dispatchers.IO) {
        dataSource.getBooksList()
    }

    override suspend fun getBook(id: String): ResultStatus<BookItem?> = withContext(Dispatchers.IO) {
        dataSource.getBookItem(id)
    }

    override suspend fun addToCart(book: BookItem): ResultStatus<Boolean> = withContext(Dispatchers.IO) {
        ResultStatus.Success(dataSource.addToCart(book))
    }

    override suspend fun removeFromCart(book: BookItem): ResultStatus<Boolean> {
        return ResultStatus.Success(dataSource.removeFromCart(book))
    }

    override suspend fun getCart() = withContext(Dispatchers.IO) {
        ResultStatus.Success(dataSource.getCartData())
    }

    override suspend fun getCheckoutConfirmationData() = withContext(Dispatchers.IO) {
        delay(2000)
        ResultStatus.Success(dataSource.getCheckoutConfirmationData())
    }

    override suspend fun orderConfirmed(): ResultStatus<OrderConfirmation> = withContext(Dispatchers.IO) {
        delay(2000)
        ResultStatus.Success(dataSource.placeOrder())
    }

    override suspend fun refreshCart(): ResultStatus<Boolean> = withContext(Dispatchers.IO) {
        ResultStatus.Success(dataSource.clearCart())
    }

    override suspend fun loginApp(username: String, password: String): ResultStatus<Boolean> = withContext(Dispatchers.IO) {
        dataSource.authenticateUser(username, password)
    }
}