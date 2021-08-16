package com.cornershop.android.kata.cornerbook.data.datasource.remote

import com.cornershop.android.kata.cornerbook.data.commons.Urls
import com.cornershop.android.kata.cornerbook.data.request.BookRequest
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse
import retrofit.Callback
import retrofit.http.Body
import retrofit.http.GET
import retrofit.http.POST

interface BookService {
    @GET(Urls.BOOKS)
    fun getBookList(
        callback: Callback<List<BookResponse>>
    )

    @POST(Urls.BOOKS)
    fun createBook(
        @Body bookRequest: BookRequest,
        callback: Callback<Unit?>
    )
}