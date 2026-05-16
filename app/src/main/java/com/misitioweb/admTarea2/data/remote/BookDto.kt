package com.misitioweb.admTarea2.data.remote

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("numFound") val numFound: Int,
    @SerializedName("docs") val docs: List<BookDto>
)

data class BookDto(
    @SerializedName("key") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("author_name") val authorNames: List<String>?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?,
    @SerializedName("cover_i") val coverI: Int?
)
