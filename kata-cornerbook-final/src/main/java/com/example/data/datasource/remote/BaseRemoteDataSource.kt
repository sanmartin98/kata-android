package com.cornershop.android.kata.cornerbook.data.datasource.remote

interface BaseRemoteDataSource<ApiDef>{
    fun getService(): ApiDef
}