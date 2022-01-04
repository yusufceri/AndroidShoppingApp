package com.example.shoppingapp.baseservicecall

import kotlinx.serialization.KSerializer

interface ServiceCall<T>: Call<T> {
    val identifier: String
    val serializer: KSerializer<T>
}