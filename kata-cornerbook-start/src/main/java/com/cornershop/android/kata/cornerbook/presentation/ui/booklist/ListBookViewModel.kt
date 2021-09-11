package com.cornershop.android.kata.cornerbook.presentation.ui.booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.logic.commons.HandledError
import com.example.logic.usecase.GetBookListUseCase
import com.cornershop.android.kata.cornerbook.presentation.BaseViewModel
import com.cornershop.android.kata.cornerbook.presentation.mapper.BookMapperView
import com.cornershop.android.kata.cornerbook.presentation.ui.models.BookView
import com.example.commons.SingleValue
import com.example.commons.setSingleValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListBookViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val bookMapperView: BookMapperView
) : BaseViewModel<ListBookEvent, ListBookState>() {

    private val _errorDialog: MutableLiveData<SingleValue<HandledError>> = MutableLiveData()
    val errorDialog: LiveData<SingleValue<HandledError>>
        get() = _errorDialog

    override fun manageEvent(event: ListBookEvent) {
        when (event) {
            ListBookEvent.InitEvent -> getBookList()
        }
    }

    private fun getBookList() {
        viewModelScope.launch(Dispatchers.Main) {
            setState(ListBookState.LoadingState)
            val result = getBookListUseCase.execute()
            result.fold(
                functionLeft = {
                    _errorDialog.setSingleValue(it)
                },
                functionRight = {
                    setState(ListBookState.DataLoadedState(it.map(bookMapperView::fromDomainToView)))
                }
            )
        }
    }

}

sealed class ListBookEvent {
    object InitEvent : ListBookEvent()
}

sealed class ListBookState {
    object LoadingState : ListBookState()
    data class DataLoadedState(val bookList: List<BookView>) : ListBookState()
}