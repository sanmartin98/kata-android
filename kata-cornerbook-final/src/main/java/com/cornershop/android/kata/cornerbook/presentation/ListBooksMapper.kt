package com.cornershop.android.kata.cornerbook.presentation

import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.presentation.models.ListBookState

object ListBooksMapper {

    fun fromDomainToViewState(list: List<Book>): List<ListBookState> {
        return list.map { fromDomainToViewState(it) }
    }

    private fun fromDomainToViewState(model: Book): ListBookState {
        return ListBookState(
            title = model.title,
            author = model.author
        )
    }
}