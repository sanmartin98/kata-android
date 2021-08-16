package com.cornershop.android.kata.cornerbook

import android.app.Application
import com.cornershop.android.kata.cornerbook.presentation.injection.Injection

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injection.init(applicationContext)
    }
}