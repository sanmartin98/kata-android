package com.cornershop.android.kata.cornerbook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<in Event, State> : ViewModel() {

    private val _screenState: MutableLiveData<State> = MutableLiveData()
    val screenState: LiveData<State>
        get() = _screenState

    fun postEvent(event: Event) {
        manageEvent(event)
    }
    protected fun setState(state:State){
        _screenState.value = state
    }
    protected fun postState(state:State){
        _screenState.postValue(state)
    }
    protected abstract fun manageEvent(event: Event)
}