package com.example.di

import com.example.logic.book.DataBookRepository
import com.example.logic.commons.DomainErrorFactory
import com.example.logic.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun providesBookRepository(
        dataBookRepository: DataBookRepository
    ) : BookRepository
}