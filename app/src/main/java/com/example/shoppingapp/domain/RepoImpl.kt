package com.example.shoppingapp.domain

import android.util.Log
import com.example.shoppingapp.data.DataSource
import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.data.model.CheckoutData
import com.example.shoppingapp.data.model.OrderConfirmation
import com.example.shoppingapp.vms.ResultStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getAllBooks(): ResultStatus<List<Book>> {
        return ResultStatus.Success(dataSource.bookDataList)
    }

    override suspend fun getBook(id: Int): ResultStatus<Book?> = withContext(Dispatchers.IO) {
        Log.d("RepoImpl", "bookId = " + id)
        ResultStatus.Success(dataSource.getBookMockData(id))
    }

    override suspend fun addToCart(book: Book): ResultStatus<Boolean> {
        return ResultStatus.Success(dataSource.addToCart(book))
    }

    override suspend fun removeFromCart(book: Book): ResultStatus<Boolean> {
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
}