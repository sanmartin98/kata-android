package com.cornershop.android.kata.cornerbook.domain.usecase

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.domain.SuspendableResultUseCase
import com.cornershop.android.kata.cornerbook.domain.commons.DomainErrorFactory
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.domain.repository.BookRepository

class CreateBookUseCase(
    private val dataBookRepository: BookRepository,
    private val errorFactory: DomainErrorFactory
) : SuspendableResultUseCase<Either<HandledError, Unit>, CreateBookUseCase.Params> {

    override suspend fun execute(params: CreateBookUseCase.Params?): Either<HandledError, Unit> {
        return dataBookRepository.createBook(
            Book(
                System.currentTimeMillis().toString(),
                params!!.title,
                params.author,
                params.description
            )
        ).map(functionLeft = errorFactory::resolveError)
    }

    data class Params(
        val title: String,
        val author: String,
        val description: String
    )
}