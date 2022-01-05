package com.example.shoppingapp.baseservicecall

object DataManager {
    suspend fun <T> fetchData(
        identifier: String
    ) : T? {
        val serviceResolver = ServiceResolver()
        return serviceResolver.resolve<T>(identifier)?.execute()
    }
}