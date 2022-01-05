package com.example.shoppingapp.servicecall

import com.example.shoppingapp.baseservicecall.JSONCall
import com.example.shoppingapp.data.model.BookList
import kotlinx.serialization.KSerializer

class GetBooks() : JSONCall<BookList> {
    companion object {
        const val IDENTIFIER = "GET_BOOK_LIST"
        const val FILE_MOCK_BOOK_LIST = "example-books-data.json"
    }

    override val fileLocation: String = FILE_MOCK_BOOK_LIST
    override val identifier: String = IDENTIFIER
    override val serializer: KSerializer<BookList> = BookList.serializer()
}