package com.cornershop.android.kata.cornerbook.domain.book

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.data.datasource.remote.BookRemoteSource
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.domain.mapper.BookMapper
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataBookRepository(
    private val bookRemoteSource: BookRemoteSource,
    private val bookMapper: BookMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookRepository {
    override suspend fun getBookList(): Either<UserErrorContainer, List<Book>> {
        return withContext(dispatcher) {
            bookRemoteSource.getBookList()
                .map(functionRight = { it.map(bookMapper::fromResponseToDomain) })
        }
    }

    override suspend fun createBook(book: Book): Either<UserErrorContainer, Unit> {
        return withContext(dispatcher){
            bookRemoteSource.createBook(bookMapper.fromDomainToRequest(book))
        }
    }
}