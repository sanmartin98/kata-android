package com.example.logic

interface SuspendableResultUseCase<out Result, in Params> {
    suspend fun execute(params: Params? = null): Result
}