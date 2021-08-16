package com.cornershop.android.kata.cornerbook.domain.mapper

import com.cornershop.android.kata.cornerbook.data.responses.BookResponse
import com.cornershop.android.kata.cornerbook.domain.model.Book

object BookMapper {

    fun fromResponseToDomain(bookResponse: BookResponse): Book {
        return Book(bookResponse.id, bookResponse.title, bookResponse.description, bookResponse.author)
    }

    fun fromResponseListToDomainList( bookResponseList: List<BookResponse>): List<Book> {
        return bookResponseList.map { fromResponseToDomain(it) }
    }
}