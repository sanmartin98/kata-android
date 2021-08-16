package com.cornershop.android.kata.cornerbook.commons

sealed class Either<out ErrorType, out ResultType> {
    class Left<ErrorType>(val error: ErrorType) : Either<ErrorType, Nothing>()
    class Right<ResultType>(val value: ResultType) : Either<Nothing, ResultType>()

    fun <T> fold(functionLeft: (ErrorType) -> T, functionRight: (ResultType) -> T): T {
        return when (this) {
            is Left -> functionLeft(error)
            is Right -> functionRight(value)
        }
    }

    @JvmName("mapRight")
    fun <NewResultType> map(functionRight: (ResultType) -> NewResultType): Either<ErrorType, NewResultType> {
        return when (this) {
            is Left -> this
            is Right -> Right(functionRight(this.value))
        }
    }

    @JvmName("mapLeft")
    fun <NewErrorType> map(
            functionLeft: (ErrorType) -> NewErrorType
    ): Either<NewErrorType, ResultType> {
        return when (this) {
            is Left -> Left(functionLeft(this.error))
            is Right -> this
        }
    }

    suspend fun <NewErrorType, NewResultType> concat(function: suspend (ResultType) -> Either<NewErrorType, NewResultType>,
                                                     errorTransformation: (error: ErrorType) -> Either<NewErrorType, Nothing>): Either<NewErrorType, NewResultType> {
        return when (this) {
            is Right -> function(this.value)
            is Left -> errorTransformation(error)
        }
    }
}