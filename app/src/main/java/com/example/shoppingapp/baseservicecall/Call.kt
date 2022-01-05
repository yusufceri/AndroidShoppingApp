package com.example.shoppingapp.baseservicecall

interface Call<T> {
    suspend fun execute(): T
}