package com.cornershop.android.kata.cornerbook.presentation.mapper

import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.presentation.ui.models.BookView

class BookMapperView {
    fun fromDomainToView(book: Book): BookView {
        return BookView(
            id = book.id,
            title = book.title,
            author = book.author,
            description = book.description
        )
    }
}