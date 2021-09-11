package com.cornershop.android.kata.cornerbook.domain.usecases

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.domain.SuspendableResultUseCase
import com.example.logic.commons.DomainErrorFactory
import com.example.logic.commons.HandledError
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.domain.repository.BookRepository

class GetBooksUseCase(
    private val repository: BookRepository,
    private val domainErrorFactory: DomainErrorFactory
) : SuspendableResultUseCase<Either<HandledError, List<Book>>, Unit> {

    override suspend fun execute(params: Unit?): Either<HandledError, List<Book>> {
      return repository.getAllBooks().map(functionLeft = domainErrorFactory::resolveError)
    }
}