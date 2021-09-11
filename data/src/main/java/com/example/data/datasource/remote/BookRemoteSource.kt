package com.example.data.datasource.remote

import com.example.commons.Either
import com.example.data.error.UserErrorContainer
import com.example.data.request.BookRequest
import com.example.data.responses.BookResponse

interface BookRemoteSource {
    suspend fun getBookList(): Either<UserErrorContainer, List<BookResponse>>
    suspend fun createBook(bookRequest: BookRequest): Either<UserErrorContainer, Unit>
}