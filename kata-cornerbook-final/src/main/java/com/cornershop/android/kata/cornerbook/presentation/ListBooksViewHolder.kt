package com.cornershop.android.kata.cornerbook.presentation

import androidx.recyclerview.widget.RecyclerView
import com.cornershop.android.kata.cornerbook.databinding.RowBookBinding
import com.cornershop.android.kata.cornerbook.presentation.models.ListBookState

class ListBooksViewHolder(private val binding: RowBookBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ListBookState) {
        with(binding) {
            tvTitle.text = data.title
            tvAuthor.text = data.author
        }
    }
}