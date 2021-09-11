package com.cornershop.android.kata.cornerbook.presentation.injection

import com.example.logic.commons.DomainErrorFactory
import com.cornershop.android.kata.cornerbook.domain.mapper.BookMapper
import com.cornershop.android.kata.cornerbook.domain.repository.BookRepository
import com.cornershop.android.kata.cornerbook.domain.repository.DataBookRepository
import com.cornershop.android.kata.cornerbook.domain.usecases.GetBooksUseCase

class DomainModuleInjector(private val dataModuleInjector: DataModuleInjector) {

    private val domainErrorFactory: DomainErrorFactory = DomainErrorFactory
    private val bookMapper: BookMapper = BookMapper
    private val dataBooksRepository: BookRepository

    private val getBooksUseCase: GetBooksUseCase by lazy {
        GetBooksUseCase(
            providesBooksRepository(),
            domainErrorFactory
        )
    }

    init {
        dataBooksRepository =
            DataBookRepository(bookMapper, dataModuleInjector.providesBookRemoteSource())
    }

    fun providesBooksRepository(): BookRepository = dataBooksRepository

    fun providesBooksUseCase(): GetBooksUseCase = getBooksUseCase
}