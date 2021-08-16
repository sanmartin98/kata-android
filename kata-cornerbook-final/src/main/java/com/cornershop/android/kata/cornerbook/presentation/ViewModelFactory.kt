package com.cornershop.android.kata.cornerbook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornershop.android.kata.cornerbook.presentation.injection.Injection

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(ListBookViewModel::class.java) -> {
                    ListBookViewModel(
                        Injection.getDomainModuleInjector().providesBooksUseCase(),
                        ListBooksMapper
                    )
                }
                else -> {
                    error("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
        }
    }
}