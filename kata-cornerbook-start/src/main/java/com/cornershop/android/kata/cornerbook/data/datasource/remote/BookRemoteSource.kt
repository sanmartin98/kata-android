package com.cornershop.android.kata.cornerbook.data.datasource.remote

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.data.request.BookRequest
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse

interface BookRemoteSource {
    suspend fun getBookList(): Either<UserErrorContainer, List<BookResponse>>
    suspend fun createBook(bookRequest: BookRequest): Either<UserErrorContainer, Unit>
}