package com.example.shoppingapp.data

import com.example.shoppingapp.baseservicecall.CallRequest
import com.example.shoppingapp.baseservicecall.TaskManager
import com.example.shoppingapp.data.mock.mockBookList
import com.example.shoppingapp.data.model.*
import com.example.shoppingapp.servicecall.GetBooks
import com.example.shoppingapp.servicecall.LoginUser
import com.example.shoppingapp.utils.roundOffDecimal
import com.example.shoppingapp.vms.ResultStatus

class DataSource {
    suspend fun getBooksList(): ResultStatus<BookList> {
        val callRequest = CallRequest(
            GetBooks.IDENTIFIER,
            CallRequest.TaskType.DATA
        )
        return TaskManager.getTask<BookList>(callRequest)?.let { bookList ->
            ResultStatus.Success(bookList)
        } ?: run {
            ResultStatus.Failure((Exception("Book List empty")))
        }
    }

    val bookDataList: List<Book>
        get() {
            return mockBookList //TODO
        }

    suspend fun getBookItem(id: String) : ResultStatus<BookItem> {
        val callRequest = CallRequest(
            GetBooks.IDENTIFIER,
            CallRequest.TaskType.DATA
        )
        TaskManager.getTask<BookList>(callRequest)?.let { bookList ->
            val book = bookList.items?.find {
                it.id == id
            }
            if(book != null)
                return ResultStatus.Success(book)
            else
                return ResultStatus.Failure(Exception("Book can't be found"))
        }
        return ResultStatus.Failure((Exception("Book List empty")))
    }

    fun getBookMockData(id: Int): Book? {
        for(book in bookDataList) {
            if(book.id == id)
                return book
        }
        return null
    }

    suspend fun addToCart(book: BookItem): Boolean {
        return CartData.putIntocart(book)
    }

    fun removeFromCart(book: BookItem): Boolean {
        return CartData.removeFromCart(book)
    }

    fun getCartData(): List<BookItem> {
        return CartData.getCartItems()
    }

    fun getCheckoutConfirmationData(): CheckoutData {
        return CheckoutData(
            shippingAddress = "Address 1",
            itemList = CartData.getCartItems(),
            estimatedTax = getEstimatedTax(CartData.getCartItems()),
            orderTotal = getOrderTotal(CartData.getCartItems())
        )
    }

    fun placeOrder(): OrderConfirmation {
        clearCart()
        return OrderConfirmation(
            orderId = 1231231,
            checkoutData = getCheckoutConfirmationData()
        )
    }

    fun clearCart(): Boolean {
        return CartData.clearCart()
    }

    suspend fun authenticateUser(username: String, password: String): ResultStatus<Boolean> {
        val callRequest = CallRequest(
            LoginUser.IDENTIFIER,
            CallRequest.TaskType.DATA
        )
        return TaskManager.getTask<LoginResponse>(callRequest)?.let { userData ->
            if(confirmUserdata(username, password, userData))
                return ResultStatus.Success(true)
            return ResultStatus.Failure((Exception("Unsuccessful Login")))
        } ?: run {
            ResultStatus.Failure((Exception("Unsuccessful Login")))
        }
    }

    /* Test purpose - User credential validation*/
    private fun confirmUserdata(username: String, password: String, loginResponse: LoginResponse): Boolean {
        if(
            username.equals(loginResponse.userCredentials?.username) &&
            password.equals(loginResponse.userCredentials?.password)
        ) {
            return true
        }
        return false
    }

    private fun getEstimatedTax(itemList: List<BookItem>): Double {
        var tax: Double = 0.0
        itemList.forEach { book ->
            book.saleInfo?.listPrice?.amount?.let {
                tax += it
            }
        }
        return (tax / 5).roundOffDecimal() ?: 0.0
    }

    private fun getOrderTotal(itemList: List<BookItem>): Double {
        var total: Double = 0.0
        itemList.forEach { book ->
            book.saleInfo?.listPrice?.amount?.let {
                total += it
            }
        }
        return (total+getEstimatedTax(itemList)).roundOffDecimal()?:0.0
    }

    companion object CartData {
        var cartItems = CartItems()
        fun putIntocart(item: BookItem): Boolean {
            return cartItems.itemList?.let { itemList ->
                itemList.add(item)
            } ?: run {
                cartItems = CartItems(mutableListOf(item))
                true
            }
        }

        fun removeFromCart(item: BookItem): Boolean {
            return cartItems.itemList?.let { itemList ->
                for(i in 0 until itemList.size-1) {
                    if(itemList[i]?.id == item.id)
                        return itemList.remove(itemList[i])
                }
                false
            } ?: run {
                false
            }
        }

        fun getCartItems(): List<BookItem> {
            return cartItems.itemList ?: listOf()
        }

        fun clearCart(): Boolean {
            cartItems.itemList?.clear()
            return true
        }
    }
}