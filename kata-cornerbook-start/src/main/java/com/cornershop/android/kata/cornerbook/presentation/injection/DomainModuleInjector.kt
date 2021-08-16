package com.cornershop.android.kata.cornerbook.presentation.injection

import com.cornershop.android.kata.cornerbook.domain.book.DataBookRepository
import com.cornershop.android.kata.cornerbook.domain.commons.DomainErrorFactory
import com.cornershop.android.kata.cornerbook.domain.mapper.BookMapper
import com.cornershop.android.kata.cornerbook.domain.usecase.CreateBookUseCase
import com.cornershop.android.kata.cornerbook.domain.usecase.GetBookListUseCase

class DomainModuleInjector(private val dataModuleInjector: DataModuleInjector) {

    private val domainErrorFactory: DomainErrorFactory = DomainErrorFactory

    private val dataBookRepository = DataBookRepository(
        dataModuleInjector.provideBookRemoteDataSource(),
        BookMapper()
    )

    private val createBookUseCase = CreateBookUseCase(dataBookRepository, domainErrorFactory)
    fun providesCreateBookUseCase(): CreateBookUseCase{
        return createBookUseCase
    }

    private val getBookListUseCase = GetBookListUseCase(dataBookRepository, domainErrorFactory)
    fun providesGetBookListUseCase(): GetBookListUseCase{
        return getBookListUseCase
    }
}