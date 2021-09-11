package com.cornershop.android.kata.cornerbook.di

import com.example.data.datasource.remote.BookRemoteDataSource
import com.example.data.datasource.remote.BookRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun providesBookRemoteSource(
        bookRemoteDataSource: BookRemoteDataSource
    ): BookRemoteSource

}