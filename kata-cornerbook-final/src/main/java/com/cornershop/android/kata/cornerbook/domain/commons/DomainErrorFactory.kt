package com.cornershop.android.kata.cornerbook.domain.commons

import com.cornershop.android.kata.cornerbook.data.error.GenericUserErrorContainer
import com.cornershop.android.kata.cornerbook.data.error.HTTPUserErrorContainer
import com.cornershop.android.kata.cornerbook.data.error.UserError
import com.cornershop.android.kata.cornerbook.data.error.UserErrorContainer
import com.cornershop.android.kata.cornerbook.domain.commons.ApiErrorCode.BOOK_NOT_VALID
import com.cornershop.android.kata.cornerbook.domain.commons.ApiErrorCode.BOOK_STORAGE_EMPTY
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError.BookNotValidError
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError.BookStorageEmptyError
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError.CommonError
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError.ExceptionError
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError.NetworkError
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError.UnhandledError

//Everytime you add this class as Error, remember add to ErrorManager and CustomLogger.logHandledError
sealed class HandledError {

    data class CommonError(
            val statusCode: Int, val reason: String,
            val errorCode: String? = null, val apiError: ApiError
    ) : HandledError() {
        fun getUserError(): String = when {
            apiError.getHumanMessageError() != null -> apiError.getHumanMessageError()!!
            else -> {
                val errorCodeMessage = if (errorCode != null) "Error code: $errorCode " else ""
                "Status Code: $statusCode " +
                        "Reason: $reason " +
                        errorCodeMessage +
                        "Message: ${apiError.error}"
            }
        }
    }

    object NetworkError : HandledError()
    class ExceptionError(val message: String) : HandledError()
    object UnhandledError : HandledError()
    data class BookNotValidError(val message: String) : HandledError()
    data class BookStorageEmptyError(val message: String) : HandledError()
}

data class ApiError(
        val error: String?,
        private val userErrorMessage: String?
//TODO Handle Product Issue error response @see APIErrorResponse
) {
    fun getHumanMessageError() = when {
        userErrorMessage != null && userErrorMessage.isNotBlank() -> userErrorMessage
        error != null && error.isNotBlank() -> error
        else -> null
    }
}

object DomainErrorFactory {
    fun resolveError(userErrorContainer: UserErrorContainer?): HandledError {
        return when (userErrorContainer) {
            is HTTPUserErrorContainer -> mapFromHttpUserErrorContainer(userErrorContainer)
            is GenericUserErrorContainer -> when (userErrorContainer.errorType) {
                UserError.Type.NETWORK -> NetworkError
                UserError.Type.EXCEPTION -> ExceptionError(userErrorContainer.message ?: "")
                else -> UnhandledError
            }
            else -> UnhandledError
        }
    }

    //Temporal method for migration
    fun resolveError(userError: UserError): HandledError {
        return when (userError.type) {
            UserError.Type.EXCEPTION -> ExceptionError(userError.message ?: "")
            UserError.Type.HTTP -> ExceptionError(userError.message ?: "")
            UserError.Type.NETWORK -> NetworkError
            UserError.Type.UNHANDLED -> UnhandledError
        }
    }

    private fun mapFromHttpUserErrorContainer(userErrorContainer: HTTPUserErrorContainer): HandledError {
        return when (userErrorContainer.code) {
            BOOK_NOT_VALID -> BookNotValidError(
                    userErrorContainer.userError.message
                            ?: ""
            )
            BOOK_STORAGE_EMPTY -> BookStorageEmptyError(
                    userErrorContainer.userError.message
                            ?: ""
            )
            else -> {
                CommonError(statusCode = userErrorContainer.status,
                        reason = userErrorContainer.reason,
                        errorCode = userErrorContainer.code,
                        apiError = with(userErrorContainer.body) {
                            ApiError(
                                    error = this.error,
                                    userErrorMessage = this.userMessage
                            )
                        }

                )
            }
        }
    }
}

object ApiErrorCode {
    const val BOOK_NOT_VALID = "BOOK_NOT_VALID"
    const val BOOK_STORAGE_EMPTY = "BOOK_STORAGE_EMPTY"
}