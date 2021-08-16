package com.cornershop.android.kata.cornerbook.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cornershop.android.kata.cornerbook.databinding.RowBookBinding
import com.cornershop.android.kata.cornerbook.presentation.models.ListBookState

class ListBooksAdapter : ListAdapter<ListBookState, ListBooksViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBooksViewHolder {
        return ListBooksViewHolder(
            RowBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListBooksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListBookState>() {

            override fun areItemsTheSame(oldItem: ListBookState, newItem: ListBookState): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListBookState, newItem: ListBookState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}