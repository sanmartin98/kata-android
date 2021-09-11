package com.example.logic.usecase

import com.example.commons.Either
import com.example.logic.SuspendableResultUseCase
import com.example.logic.commons.DomainErrorFactory
import com.example.logic.commons.HandledError
import com.example.logic.model.Book
import com.example.logic.repository.BookRepository
import javax.inject.Inject

class CreateBookUseCase @Inject constructor(
    private val dataBookRepository: BookRepository,
    private val errorFactory: DomainErrorFactory
) : SuspendableResultUseCase<Either<HandledError, Unit>, CreateBookUseCase.Params> {

    override suspend fun execute(params: Params?): Either<HandledError, Unit> {
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