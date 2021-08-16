package com.cornershop.android.kata.cornerbook.domain.usecase

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.domain.SuspendableResultUseCase
import com.cornershop.android.kata.cornerbook.domain.book.DataBookRepository
import com.cornershop.android.kata.cornerbook.domain.commons.DomainErrorFactory
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError
import com.cornershop.android.kata.cornerbook.domain.model.Book

class GetBookListUseCase(
    private val dataBookRepository: DataBookRepository,
    private val errorFactory: DomainErrorFactory
): SuspendableResultUseCase<Either<HandledError, List<Book>>, Unit> {

    override suspend fun execute(params: Unit?): Either<HandledError, List<Book>> {
        return dataBookRepository.getBookList().map(functionLeft = errorFactory::resolveError)
    }
}