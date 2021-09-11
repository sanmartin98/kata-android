package com.example.logic.usecase

import com.example.commons.Either
import com.example.logic.SuspendableResultUseCase
import com.example.logic.commons.DomainErrorFactory
import com.example.logic.commons.HandledError
import com.example.logic.model.Book
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