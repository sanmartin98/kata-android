package com.cornershop.android.kata.cornerbook.presentation.ui.createbook

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import com.cornershop.android.kata.cornerbook.R
import com.cornershop.android.kata.cornerbook.databinding.ActivityCreateBookBinding
import com.example.logic.commons.HandledError
import com.cornershop.android.kata.cornerbook.presentation.utils.DialogUtils
import com.example.commons.observeSingleValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBookActivity : AppCompatActivity() {
    private val binding: ActivityCreateBookBinding by lazy { ActivityCreateBookBinding.inflate(layoutInflater) }
    private val viewModel: CreateBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpView()
        observeViewModelChange()
    }

    private fun setUpView(){
        setUpTextsListeners()
        binding.buttonCreateBook.setOnClickListener { validateFields() }
    }

    private fun setUpTextsListeners(){
        binding.editTextTitle.addTextChangedListener { binding.layoutTitle.error = null }
        binding.editTextAuthor.addTextChangedListener { binding.layoutAuthor.error = null }
        binding.editTextDescription.addTextChangedListener { binding.layoutDescription.error = null }
    }

    private fun observeViewModelChange(){
        viewModel.screenState.observe(this){ state ->
            when(state){
                CreateBookViewState.LoadingCreateBookState -> showProgressBar()
                CreateBookViewState.BookCreatedState -> backToBookList()
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

    private fun backToBookList(){
        hideProgressBar()
        setResult(RESULT_OK, Intent())
        finish()
    }

    private fun validateFields() {
        val title = binding.editTextTitle.text.toString()
        val author = binding.editTextAuthor.text.toString()
        val description = binding.editTextDescription.text.toString()

        if (title.isEmpty() || author.isEmpty() || description.isEmpty()) {
            if (title.isEmpty()) binding.layoutTitle.error =
                getString(R.string.error_title_create_book)
            if (author.isEmpty()) binding.layoutAuthor.error =
                getString(R.string.error_author_create_book)
            if (description.isEmpty()) binding.layoutDescription.error =
                getString(R.string.error_description_create_book)
        } else {
            viewModel.postEvent(CreateBookEvent.CreateEvent(title, author, description))
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.progressBar.isGone = true
    }
}