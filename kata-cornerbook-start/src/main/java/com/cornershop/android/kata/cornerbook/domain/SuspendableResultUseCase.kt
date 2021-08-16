package com.cornershop.android.kata.cornerbook.domain

interface SuspendableResultUseCase<out Result, in Params> {
    suspend fun execute(params: Params? = null): Result
}