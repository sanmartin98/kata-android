package com.cornershop.android.kata.cornerbook.presentation.mapper

import com.cornershop.android.kata.cornerbook.presentation.ui.models.BookView
import com.example.logic.model.Book
import javax.inject.Inject

class BookMapperView @Inject constructor() {
    fun fromDomainToView(book: Book): BookView {
        return BookView(
            id = book.id,
            title = book.title,
            author = book.author,
            description = book.description
        )
    }
}