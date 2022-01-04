package com.example.shoppingapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookList(
    val totalItems: Int?,
    val items: List<BookItem>?
)

@Serializable
data class BookItem(
    val kind: String?,
    val id: String?,
    val volumeInfo: VolumeInfo?,
    val searchInfo: SearchInfo? = null,
    val saleInfo: SaleInfo? = null
) : SPDataModel()

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val averageRating: Float? = null,
    val ratingsCount: Int? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)

@Serializable
data class SearchInfo(
    val textSnippet: String?
)

@Serializable
data class SaleInfo(
    val listPrice: ListPrice? = null
)

@Serializable
data class ListPrice(
    val amount: Double? = null,
    val currencyCode: String? = null
)

