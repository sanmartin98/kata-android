package com.cornershop.android.kata.cornerbook.domain.usecase

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.domain.SuspendableResultUseCase
import com.cornershop.android.kata.cornerbook.domain.commons.DomainErrorFactory
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.example.logic.repository.BookRepository
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val dataBookRepository: BookRepository,
    private val errorFactory: DomainErrorFactory
): SuspendableResultUseCase<Either<HandledError, List<Book>>, Unit> {

    override suspend fun execute(params: Unit?): Either<HandledError, List<Book>> {
        return dataBookRepository.getBookList().map(functionLeft = errorFactory::resolveError)
    }
}