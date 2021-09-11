package com.cornershop.android.kata.cornerbook.di

import android.util.Log
import com.example.data.commons.DateDeserializer
import com.example.data.datasource.remote.BookService
import com.cornershop.android.kata.cornerbook.domain.commons.DomainErrorFactory
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit.Ok3Client
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit.RestAdapter
import retrofit.converter.GsonConverter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): RestAdapter{
        return RestAdapter.Builder()
            .setEndpoint("http://192.168.1.62:8080")
            .setLog { s -> Log.d("CLIENT API", s) }
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setClient(Ok3Client(okHttpClient))
            .setConverter(
                GsonConverter(
                GsonBuilder().registerTypeAdapter(Date::class.java, DateDeserializer())
                    .create())
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient{
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(3, TimeUnit.MINUTES)
        builder.readTimeout(3, TimeUnit.MINUTES)
        builder.writeTimeout(3, TimeUnit.MINUTES)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideBookService(restAdapter: RestAdapter): BookService {
        return restAdapter.create(BookService::class.java)
    }

    @Singleton
    @Provides
    fun providesDomainErrorFactory(): DomainErrorFactory {
        return DomainErrorFactory
    }
}