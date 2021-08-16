package com.cornershop.android.kata.cornerbook.presentation.ui.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.android.kata.cornerbook.databinding.RowBookBinding
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.presentation.ui.models.BookView

class ListBookAdapter : RecyclerView.Adapter<ListBookAdapter.ViewHolder>() {

    private var bookList = mutableListOf<BookView>()

    fun setBookList(bookList: List<BookView>) {
        this.bookList.clear()
        this.bookList.addAll(bookList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class ViewHolder(private val binding: RowBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookView){
            binding.textViewTitleBook.text = item.title
            binding.textViewAuthorBook.text = item.author
            binding.textViewDescriptionBook.text = item.description
        }
    }
}