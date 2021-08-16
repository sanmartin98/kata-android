package com.cornershop.android.kata.cornerbook.presentation.models

sealed class ListBooksViewStates {
    object LoadingState : ListBooksViewStates()
    object EmptyListState : ListBooksViewStates()
    data class DataLoadedState(val lisBooks: List<ListBookState>) : ListBooksViewStates()
}
