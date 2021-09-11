package com.cornershop.android.kata.cornerbook.data.commons

import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit.Ok3Client
import okhttp3.OkHttpClient
import retrofit.RestAdapter.Builder
import retrofit.RestAdapter.LogLevel.FULL
import retrofit.converter.GsonConverter
import java.util.Date
import java.util.concurrent.TimeUnit

object RestServiceManager {

    const val ENDPOINT = "http://192.168.10.10:8080"

    val restAdapter by lazy {
        Builder()
                .setEndpoint(ENDPOINT)
                .setLog { s -> Log.d("CLIENT API", s) }
                .setLogLevel(FULL)
                .setClient(Ok3Client(buildRetrofitClient()))
                .setConverter(GsonConverter(
                        GsonBuilder().registerTypeAdapter(Date::class.java, DateDeserializer())
                                .create()))
                .build()
    }

    private fun buildRetrofitClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(3, TimeUnit.MINUTES)
        builder.readTimeout(3, TimeUnit.MINUTES)
        builder.writeTimeout(3, TimeUnit.MINUTES)

        return builder.build()
    }
}