package com.cornershop.android.kata.cornerbook.presentation.models

sealed class ListBooksEvents {
    object InitScreenEvent : ListBooksEvents()
    object OnBookClicked: ListBooksEvents()
}
