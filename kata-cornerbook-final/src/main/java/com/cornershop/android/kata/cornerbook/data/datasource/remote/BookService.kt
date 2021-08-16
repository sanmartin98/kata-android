package com.cornershop.android.kata.cornerbook.data.datasource.remote

import com.cornershop.android.kata.cornerbook.data.commons.Urls
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse
import retrofit.Callback
import retrofit.http.GET

interface BookService {

    @GET(Urls.BOOKS)
    fun listAllBooks(callback: Callback<List<BookResponse>>)

}