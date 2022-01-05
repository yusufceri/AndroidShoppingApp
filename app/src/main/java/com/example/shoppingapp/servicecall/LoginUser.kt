package com.example.shoppingapp.servicecall

import com.example.shoppingapp.baseservicecall.JSONCall
import com.example.shoppingapp.data.model.LoginResponse
import kotlinx.serialization.KSerializer

class LoginUser: JSONCall<LoginResponse> {
    companion object {
        const val IDENTIFIER = "AUTHENTICATE_USER"
        const val FILE_MOCK_USER = "user.json"
    }

    override val fileLocation: String = FILE_MOCK_USER
    override val identifier: String = IDENTIFIER
    override val serializer: KSerializer<LoginResponse> = LoginResponse.serializer()
}