package com.cornershop.android.kata.cornerbook.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.android.kata.cornerbook.commons.observeSingleValue
import com.cornershop.android.kata.cornerbook.databinding.ActivityListBookBinding
import com.example.logic.commons.HandledError
import com.cornershop.android.kata.cornerbook.presentation.models.ListBookState
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksEvents
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates
import com.cornershop.android.kata.cornerbook.presentation.utils.DialogUtils

class ListBookActivity : AppCompatActivity() {

    private val viewModel: ListBookViewModel by viewModels { ViewModelFactory() }
    private val binding: ActivityListBookBinding by lazy {
        ActivityListBookBinding.inflate(
            layoutInflater
        )
    }
    private val booksAdapter: ListBooksAdapter by lazy { ListBooksAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        observeViewModelChanges()
        viewModel.postEvent(ListBooksEvents.InitScreenEvent)
    }

    private fun initViews() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = booksAdapter
    }

    private fun observeViewModelChanges() {
        viewModel.screenState.observe(this) { state ->
            when (state) {
                is ListBooksViewStates.DataLoadedState -> {
                    hideLoading()
                    showData(state.lisBooks)
                }
                ListBooksViewStates.LoadingState -> {
                    showLoading()
                    hideUI()
                }
            }
        }

        viewModel.errorDialog.observeSingleValue(this) { error ->
            val message: String = when (error) {
                is HandledError.BookNotValidError -> {
                    "BookError"
                }
                is HandledError.BookStorageEmptyError -> {
                    "Empty List"
                }
                is HandledError.CommonError -> {
                    error.reason
                }
                is HandledError.ExceptionError -> {
                    error.message
                }
                HandledError.NetworkError -> {
                    "No tienes conexion"
                }
                HandledError.UnhandledError -> {
                    "Error inesperado"
                }

            }
            DialogUtils.showDialog(ListBookActivity@ this, "Error", message) { }

        }
    }

    private fun hideUI() {
        binding.rvList.isVisible = false
    }

    private fun showData(listBooks: List<ListBookState>) {
        binding.rvList.isVisible = true
        booksAdapter.submitList(listBooks)
    }

    private fun showLoading() {
        binding.loading.isVisible = true
    }

    private fun hideLoading() {
        binding.loading.isVisible = false
    }
}