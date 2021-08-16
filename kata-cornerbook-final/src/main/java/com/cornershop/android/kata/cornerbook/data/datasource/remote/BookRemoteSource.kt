package com.cornershop.android.kata.cornerbook.data.datasource.remote

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse

interface BookRemoteSource {

    suspend fun getBooksList(): Either<UserErrorContainer, List<BookResponse>>

}