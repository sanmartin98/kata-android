package com.cornershop.android.kata.cornerbook.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cornershop.android.kata.cornerbook.commons.SingleValue
import com.cornershop.android.kata.cornerbook.commons.setSingleValue
import com.example.logic.commons.HandledError
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.domain.usecases.GetBooksUseCase
import com.cornershop.android.kata.cornerbook.presentation.bases.BaseViewModel
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksEvents
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates.DataLoadedState
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates.EmptyListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListBookViewModel(
        private val getBooksUseCase: GetBooksUseCase,
        private val booksMapper: ListBooksMapper,
) : BaseViewModel<ListBooksEvents, ListBooksViewStates>() {

    private val _errorDialog: MutableLiveData<SingleValue<HandledError>> = MutableLiveData()
    val errorDialog: LiveData<SingleValue<HandledError>>
        get() = _errorDialog

    override fun manageEvent(event: ListBooksEvents) {
        when (event) {
            ListBooksEvents.InitScreenEvent -> {
                searchBooks()
            }
            ListBooksEvents.OnBookClicked -> {

            }
        }
    }

    private fun searchBooks() {
        viewModelScope.launch(Dispatchers.Main) {
            setState(ListBooksViewStates.LoadingState)

            val result = getBooksUseCase.execute()

            result.fold(functionLeft = {
                onError(it)
            }, functionRight = {
                onSuccess(it)
            })
        }
    }

    private fun onSuccess(bookList: List<Book>) {
        if (bookList.isEmpty()) {
            setState(EmptyListState)
        } else {
            setState(
                    DataLoadedState(
                            booksMapper.fromDomainToViewState(bookList)
                    )
            )
        }
    }

    private fun onError(error: HandledError) {
        _errorDialog.setSingleValue(error)
    }
}