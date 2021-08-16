package com.cornershop.android.kata.server.model

import kotlinx.serialization.Serializable

val bookStorage = mutableListOf<Book>()

@Serializable
data class Book(
    val id: String,
    val title: String,
    val description: String,
    val author: String
)