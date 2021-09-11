package com.cornershop.android.kata.cornerbook.domain.repository

import com.cornershop.android.kata.cornerbook.commons.Either
import com.example.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.domain.model.Book

interface BookRepository {
    suspend fun getBookList(): Either<UserErrorContainer, List<Book>>
    suspend fun createBook(book: Book): Either<UserErrorContainer, Unit>
}