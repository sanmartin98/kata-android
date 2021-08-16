package com.cornershop.android.kata.cornerbook.domain.repository

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.domain.model.Book

interface BookRepository {

    suspend fun getAllBooks(): Either<UserErrorContainer, List<Book>>

}