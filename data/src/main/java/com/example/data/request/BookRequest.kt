package com.cornershop.android.kata.cornerbook.data.request

import com.google.gson.annotations.SerializedName

data class BookRequest (
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String
    )