package com.cornershop.android.ktor.server.model;

import kotlinx.serialization.Serializable

@Serializable
data class APIErrorResponse(
    val error: String,
    val userErrorMsg: String
)