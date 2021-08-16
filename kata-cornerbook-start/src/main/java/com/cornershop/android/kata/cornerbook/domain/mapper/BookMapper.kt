package com.cornershop.android.kata.cornerbook.domain.mapper

import com.cornershop.android.kata.cornerbook.data.request.BookRequest
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse
import com.cornershop.android.kata.cornerbook.domain.model.Book

class BookMapper {
    fun fromResponseToDomain(bookResponse: BookResponse): Book{
        return Book(
            id = bookResponse.id,
            title = bookResponse.title,
            author = bookResponse.author,
            description = bookResponse.description
        )
    }

    fun fromDomainToRequest(book: Book): BookRequest{
        return BookRequest(
            id = book.id,
            title = book.title,
            author = book.author,
            description = book.description
        )
    }
}