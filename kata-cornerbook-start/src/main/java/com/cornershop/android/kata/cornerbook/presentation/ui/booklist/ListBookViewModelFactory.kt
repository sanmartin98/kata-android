package com.cornershop.android.kata.cornerbook.presentation.ui.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornershop.android.kata.cornerbook.presentation.injection.Injection
import com.cornershop.android.kata.cornerbook.presentation.mapper.BookMapperView

object ListBookViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass){
            when{
                isAssignableFrom(ListBookViewModel::class.java) -> ListBookViewModel(
                    Injection.getDomainModuleInjector().providesGetBookListUseCase(),
                    BookMapperView()
                )
                else -> error("Unknown ViewModel class: ${modelClass.name}")
            } as T
        }
    }
}