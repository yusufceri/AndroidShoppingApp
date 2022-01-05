package com.example.shoppingapp.baseservicecall

import android.app.Application
import android.content.Context
import com.example.shoppingapp.app.SPApplication
import com.example.shoppingapp.utils.ParsingUtils
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.reflect.Type

interface JSONCall<T>: ServiceCall<T> {
    val fileLocation: String
    override val identifier: String
    override val serializer: KSerializer<T>

    override suspend fun execute(): T {
        return ParsingUtils.parseJson(SPApplication.appContext, fileLocation, serializer)
    }
}