package com.example.shoppingapp.baseservicecall

import com.example.shoppingapp.servicecall.GetBooks
import com.example.shoppingapp.servicecall.LoginUser

class ServiceResolver {
    fun <T> resolve(identifier: String): ServiceCall<T>? {
        when (identifier) {
            GetBooks.IDENTIFIER ->
                return GetBooks() as JSONCall<T>
            LoginUser.IDENTIFIER ->
                return LoginUser() as JSONCall<T>
        }
        return null
    }
}