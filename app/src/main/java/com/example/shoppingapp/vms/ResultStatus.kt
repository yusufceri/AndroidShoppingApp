package com.example.shoppingapp.vms

sealed class ResultStatus<out T> {
    class Loading<out T> : ResultStatus<T>()
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Failure<out T>(val exception: Exception) : ResultStatus<T>()
}
