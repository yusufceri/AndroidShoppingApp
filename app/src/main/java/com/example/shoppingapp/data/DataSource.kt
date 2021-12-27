package com.example.shoppingapp.data

import com.example.shoppingapp.data.model.Book
import com.example.shoppingapp.data.model.CartItems
import com.example.shoppingapp.data.model.CheckoutData
import com.example.shoppingapp.data.model.OrderConfirmation

class DataSource {
    suspend fun getAllBooksFromApi(): Result<List<Book>> {
        return Result.success(bookDataList)
    }

    val bookDataList = listOf(
        Book(1,
            "Example1",
            "Author1",
            12.0),
        Book(2,
            "Example2",
            "Author2",
            13.0),
        Book(3,
            "Example3",
            "Author3",
            14.0),
        Book(4,
            "Example4",
            "Author4",
            14.0),
        Book(5,
            "Example5",
            "Author5",
            16.0
        ),
        Book(6,
            "Example6",
            "Author6",
            18.0),
        Book(7,
            "Example7",
            "Author7",
            19.0)
    )

    fun getBookMockData(id: Int): Book? {
        for(book in bookDataList) {
            if(book.id == id)
                return book
        }
        return null
    }

    fun addToCart(book: Book): Boolean {
        return CartData.putIntocart(book)
    }

    fun removeFromCart(book: Book): Boolean {
        return CartData.removeFromCart(book)
    }

    fun getCartData(): List<Book> {
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

    private fun getEstimatedTax(itemList: List<Book>): Double {
        var tax: Double = 0.0
        itemList.forEach { book ->
            tax += book.price
        }
        return tax / 5
    }

    private fun getOrderTotal(itemList: List<Book>): Double {
        var total: Double = 0.0
        itemList.forEach { book ->
            total += book.price
        }
        return total
    }

    companion object CartData {
        var cartItems = CartItems()
        fun putIntocart(book: Book): Boolean {
            return cartItems.bookList?.let { bookList ->
                bookList.add(book)
            } ?: run {
                cartItems = CartItems(mutableListOf(book))
                true
            }
        }

        fun removeFromCart(book: Book): Boolean {
            return cartItems.bookList?.let { list ->
                for(i in 0..list.size) {
                    if(list[i].id == book.id)
                        return list.remove(book)
                }
                false
            } ?: run {
                false
            }
        }

        fun getCartItems(): List<Book> {
            return cartItems.bookList ?: listOf()
        }

        fun clearCart(): Boolean {
            cartItems.bookList?.clear()
            return true
        }
    }
}