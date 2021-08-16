package com.cornershop.android.kata.cornerbook.presentation.injection

import android.content.Context
import com.cornershop.android.kata.cornerbook.data.commons.RestServiceManager
import com.cornershop.android.kata.cornerbook.data.datasource.remote.BookRemoteDataSource
import com.cornershop.android.kata.cornerbook.data.datasource.remote.BookRemoteSource
import com.cornershop.android.kata.cornerbook.data.error.ErrorFactory

class DataModuleInjector(context: Context) {

    private val restServiceManager = RestServiceManager
    private val errorFactory = ErrorFactory()

    private val bookRemoteDataSource = BookRemoteDataSource(restServiceManager, errorFactory)

    fun provideBookRemoteDataSource(): BookRemoteSource{
        return bookRemoteDataSource
    }
}