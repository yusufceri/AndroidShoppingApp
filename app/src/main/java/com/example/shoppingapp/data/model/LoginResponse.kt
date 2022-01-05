package com.example.shoppingapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val isSuccessful: Boolean?,
    val userId: String?,
    val address: String?,
    val customerName: String?,
    val deviceId: String?,
    val userCredentials: UserCredentials?
)

@Serializable
data class UserCredentials(
    val username: String,
    val password: String
)