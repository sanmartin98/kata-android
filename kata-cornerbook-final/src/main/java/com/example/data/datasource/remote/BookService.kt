package com.example.data.datasource.remote

import com.example.data.commons.Urls
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse
import retrofit.Callback
import retrofit.http.GET

interface BookService {

    @GET(Urls.BOOKS)
    fun listAllBooks(callback: Callback<List<BookResponse>>)

}