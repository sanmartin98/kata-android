package com.cornershop.android.kata.cornerbook.presentation.ui.booklist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.android.kata.cornerbook.databinding.ActivityListBookBinding
import com.example.logic.commons.HandledError
import com.cornershop.android.kata.cornerbook.presentation.ui.createbook.CreateBookActivity
import com.cornershop.android.kata.cornerbook.presentation.ui.models.BookView
import com.cornershop.android.kata.cornerbook.presentation.utils.DialogUtils
import com.example.commons.observeSingleValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListBookActivity : AppCompatActivity() {
    private val binding: ActivityListBookBinding by lazy { ActivityListBookBinding.inflate(layoutInflater) }
    private lateinit var bookAdapter: ListBookAdapter
    private val viewModel: ListBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpView()
        observeViewModelChange()
        loadBookList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadBookList()
    }

    private fun setUpView(){
        bookAdapter = ListBookAdapter()
        binding.reciclerViewBooks.layoutManager = LinearLayoutManager(this)
        binding.reciclerViewBooks.adapter = bookAdapter
        binding.buttonCreateBook.setOnClickListener{
            val intent = Intent(this, CreateBookActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    private fun loadBookList(){
        viewModel.postEvent(ListBookEvent.InitEvent)
    }

    private fun observeViewModelChange(){
        viewModel.screenState.observe(this){ state ->
            when(state){
                ListBookState.LoadingState -> showProgressBar()
                is ListBookState.DataLoadedState -> showUI(state.bookList)
            }
        }

        viewModel.errorDialog.observeSingleValue(this){
            val message = when(it){
                is HandledError.BookNotValidError -> "Book not valid"
                is HandledError.BookStorageEmptyError -> "Book storage empty"
                is HandledError.CommonError -> "Error"
                is HandledError.ExceptionError -> "Exception"
                HandledError.NetworkError -> "Network error"
                HandledError.UnhandledError -> "Unexpected error"
            }
            DialogUtils.showDialog(this, "Error", message) {}
        }
    }

    private fun showUI(bookList: List<BookView>) {
        hideProgressBar()
        bookAdapter.setBookList(bookList)
    }

    private fun showProgressBar() {
        binding.progressBarContainer.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.progressBar.isGone = true
    }
}