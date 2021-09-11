package com.example.di

import com.example.logic.commons.DomainErrorFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorFactoryModule {
    @Singleton
    @Provides
    fun providesDomainErrorFactory(): DomainErrorFactory {
        return DomainErrorFactory
    }
}