package com.example.data.datasource.remote

import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.data.error.ErrorFactory
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.data.request.BookRequest
import com.cornershop.android.kata.cornerbook.data.responses.BookResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import javax.inject.Inject
import kotlin.coroutines.resume

class BookRemoteDataSource @Inject constructor(
    private val errorFactory: ErrorFactory,
    private val service: BookService
): BookRemoteSource {

    override suspend fun getBookList(): Either<UserErrorContainer, List<BookResponse>> {
        return suspendCancellableCoroutine { continuation ->
            service.getBookList(object: Callback<List<BookResponse>>{
                override fun success(t: List<BookResponse>, response: Response?) {
                    continuation.resume(Either.Right(t))
                }

                override fun failure(error: RetrofitError?) {
                    continuation.resume(Either.Left(errorFactory.makeError(error)))
                }
            })
        }
    }

    override suspend fun createBook(bookRequest: BookRequest): Either<UserErrorContainer, Unit> {
        return suspendCancellableCoroutine { continuation ->
            service.createBook(bookRequest, object: Callback<Unit?>{
                override fun success(t: Unit?, response: Response?) {
                    continuation.resume(Either.Right(Unit))
                }

                override fun failure(error: RetrofitError?) {
                    continuation.resume(Either.Left(errorFactory.makeError(error)))
                }
            })
        }
    }

}