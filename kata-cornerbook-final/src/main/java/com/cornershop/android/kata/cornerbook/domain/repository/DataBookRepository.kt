package com.cornershop.android.kata.cornerbook.domain.repository

import com.cornershop.android.kata.cornerbook.commons.Either
import com.example.data.datasource.remote.BookRemoteSource
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.domain.mapper.BookMapper
import com.cornershop.android.kata.cornerbook.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataBookRepository(
        private val bookMapper: BookMapper,
        private val bookRemoteSource: BookRemoteSource,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookRepository {
    override suspend fun getAllBooks(): Either<UserErrorContainer, List<Book>> {
        return withContext(dispatcher) {
            bookRemoteSource.getBooksList().map(functionRight = bookMapper::fromResponseListToDomainList)
        }
    }

}