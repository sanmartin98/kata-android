package com.example.data.datasource.remote

interface BaseRemoteDataSource<ApiDef>{
    fun getService(): ApiDef
}