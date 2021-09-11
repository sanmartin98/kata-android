package com.example.data.datasource.remote

import com.example.data.commons.Urls
import com.example.data.request.BookRequest
import com.example.data.responses.BookResponse
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