package com.example.logic.repository

import com.example.data.error.UserErrorContainer
import com.example.commons.Either
import com.example.logic.model.Book

interface BookRepository {
    suspend fun getBookList(): Either<UserErrorContainer, List<Book>>
    suspend fun createBook(book: Book): Either<UserErrorContainer, Unit>
}