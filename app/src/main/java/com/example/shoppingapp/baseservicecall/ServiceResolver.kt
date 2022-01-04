package com.example.shoppingapp.baseservicecall

import com.example.shoppingapp.servicecall.GetBooks

class ServiceResolver {
    fun <T> resolve(identifier: String): ServiceCall<T>? {
        when (identifier) {
            GetBooks.IDENTIFIER ->
                return GetBooks() as JSONCall<T>
        }
        return null
    }
}