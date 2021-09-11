package com.cornershop.android.kata.cornerbook.data.datasource.remote

import com.cornershop.android.kata.cornerbook.commons.Either
import com.example.data.commons.RestServiceManager
import com.cornershop.android.kata.cornerbook.data.error.ErrorFactory
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import kotlin.coroutines.resume

class BookRemoteDataSource(
    private val restServiceManager: RestServiceManager,
    private val errorFactory: ErrorFactory) : BaseRemoteDataSource<BookService>, BookRemoteSource {
    override fun getService(): BookService {
        return restServiceManager.restAdapter.create(BookService::class.java)
    }

    override suspend fun getBooksList(): Either<UserErrorContainer, List<BookResponse>> {
        return suspendCancellableCoroutine { continuation ->
            getService().listAllBooks(object : Callback<List<BookResponse>> {
                override fun success(t: List<BookResponse>, response: Response?) {
                    continuation.resume(Either.Right(t))
                }

                override fun failure(error: RetrofitError?) {
                    continuation.resume(Either.Left(errorFactory.makeError(error)))
                }
            })
        }
    }
}