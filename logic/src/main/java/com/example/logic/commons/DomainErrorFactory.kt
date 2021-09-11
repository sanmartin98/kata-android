package com.example.logic.commons

import com.example.data.error.GenericUserErrorContainer
import com.example.data.error.HTTPUserErrorContainer
import com.example.data.error.UserError
import com.example.data.error.UserErrorContainer

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
                UserError.Type.NETWORK -> HandledError.NetworkError
                UserError.Type.EXCEPTION -> HandledError.ExceptionError(userErrorContainer.message ?: "")
                else -> HandledError.UnhandledError
            }
            else -> HandledError.UnhandledError
        }
    }

    //Temporal method for migration
    fun resolveError(userError: UserError): HandledError {
        return when (userError.type) {
            UserError.Type.EXCEPTION -> HandledError.ExceptionError(userError.message ?: "")
            UserError.Type.HTTP -> HandledError.ExceptionError(userError.message ?: "")
            UserError.Type.NETWORK -> HandledError.NetworkError
            UserError.Type.UNHANDLED -> HandledError.UnhandledError
        }
    }

    private fun mapFromHttpUserErrorContainer(userErrorContainer: HTTPUserErrorContainer): HandledError {
        return when (userErrorContainer.code) {
            ApiErrorCode.BOOK_NOT_VALID -> HandledError.BookNotValidError(
                userErrorContainer.userError.message
                    ?: ""
            )
            ApiErrorCode.BOOK_STORAGE_EMPTY -> HandledError.BookStorageEmptyError(
                userErrorContainer.userError.message
                    ?: ""
            )
            else -> {
                HandledError.CommonError(statusCode = userErrorContainer.status,
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
