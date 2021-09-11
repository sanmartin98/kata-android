package com.cornershop.android.kata.cornerbook.domain.mapper

import com.example.data.request.BookRequest
import com.example.data.responses.BookResponse
import com.cornershop.android.kata.cornerbook.domain.model.Book
import javax.inject.Inject

class BookMapper @Inject constructor() {
    fun fromResponseToDomain(bookResponse: BookResponse): Book{
        return Book(
            id = bookResponse.id,
            title = bookResponse.title,
            author = bookResponse.author,
            description = bookResponse.description
        )
    }

    fun fromDomainToRequest(book: Book): BookRequest {
        return BookRequest(
            id = book.id,
            title = book.title,
            author = book.author,
            description = book.description
        )
    }
}