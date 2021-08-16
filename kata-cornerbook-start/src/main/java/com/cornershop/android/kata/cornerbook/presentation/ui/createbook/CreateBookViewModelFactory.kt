package com.cornershop.android.kata.cornerbook.presentation.ui.createbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornershop.android.kata.cornerbook.presentation.injection.Injection

object CreateBookViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass){
            when{
                isAssignableFrom(CreateBookViewModel::class.java) -> CreateBookViewModel(
                    Injection.getDomainModuleInjector().providesCreateBookUseCase()
                )
                else -> error("Unknown ViewModel class: ${modelClass.name}")
            } as T
        }
    }
}