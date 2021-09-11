package com.cornershop.android.kata.cornerbook.domain.book

import com.cornershop.android.kata.cornerbook.commons.Either
import com.example.data.datasource.remote.BookRemoteSource
import com.example.data.error.UserErrorContainer
import com.example.logic.mapper.BookMapper
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.example.logic.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBookRepository @Inject constructor(
    private val bookRemoteSource: BookRemoteSource,
    private val bookMapper: BookMapper
) : BookRepository {
    override suspend fun getBookList(): Either<UserErrorContainer, List<Book>> {
        return withContext(Dispatchers.IO) {
            bookRemoteSource.getBookList()
                .map(functionRight = { it.map(bookMapper::fromResponseToDomain) })
        }
    }

    override suspend fun createBook(book: Book): Either<UserErrorContainer, Unit> {
        return withContext(Dispatchers.IO){
            bookRemoteSource.createBook(bookMapper.fromDomainToRequest(book))
        }
    }
}