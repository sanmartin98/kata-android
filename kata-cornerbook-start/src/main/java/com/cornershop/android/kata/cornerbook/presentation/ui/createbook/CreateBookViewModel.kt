package com.cornershop.android.kata.cornerbook.presentation.ui.createbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cornershop.android.kata.cornerbook.commons.SingleValue
import com.cornershop.android.kata.cornerbook.commons.setSingleValue
import com.cornershop.android.kata.cornerbook.domain.commons.HandledError
import com.cornershop.android.kata.cornerbook.domain.usecase.CreateBookUseCase
import com.cornershop.android.kata.cornerbook.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateBookViewModel(
    private val createBookUseCase: CreateBookUseCase
) : BaseViewModel<CreateBookEvent, CreateBookViewState>() {

    private val _errorDialog: MutableLiveData<SingleValue<HandledError>> = MutableLiveData()
    val errorDialog: LiveData<SingleValue<HandledError>>
        get() = _errorDialog

    override fun manageEvent(event: CreateBookEvent) {
        when (event) {
            is CreateBookEvent.CreateEvent -> createBook(event.title, event.author, event.description)
        }
    }

    private fun createBook(title: String, author: String, description: String) {
        viewModelScope.launch(Dispatchers.Main) {
            setState(CreateBookViewState.LoadingCreateBookState)
            createBookUseCase.execute(CreateBookUseCase.Params(title, author, description)).fold(
                functionLeft = {
                    _errorDialog.setSingleValue(it)
                },
                functionRight = {
                    setState(CreateBookViewState.BookCreatedState)
                }
            )
        }
    }
}

sealed class CreateBookEvent {
    data class CreateEvent(
        val title: String,
        val author: String,
        val description: String
    ) : CreateBookEvent()
}

sealed class CreateBookViewState {
    object LoadingCreateBookState : CreateBookViewState()
    object BookCreatedState : CreateBookViewState()
}