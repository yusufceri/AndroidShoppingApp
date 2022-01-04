package com.example.shoppingapp.utils

import android.content.Context
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object ParsingUtils {
    fun <T> parseJson(context: Context, fileLocation: String, serializer: KSerializer<T>): T {
        val readData = readJsonFromInputStream(context.assets.open(fileLocation))
        return Json{ignoreUnknownKeys = true}.decodeFromString<T>(serializer, readData)
    }

    @Throws(IOException::class)
    fun readJsonFromInputStream(inputStream: InputStream): String {
        var jsonData: String?
        var size = inputStream.available()
        var readBuffer = ByteArray(size)
        inputStream.read(readBuffer)
        inputStream.close()
        jsonData = String(readBuffer, Charset.forName("UTF-8"))
        return jsonData
    }
}