package com.cornershop.android.kata.cornerbook.di

import com.example.logic.book.DataBookRepository
import com.example.logic.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun providesBookRepository(
        dataBookRepository: DataBookRepository
    ) : BookRepository

}