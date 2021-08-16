package com.cornershop.android.kata.cornerbook.presentation.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {

    fun showDialog(
        context: Context,
        title: String,
        message: String,
        positiveClickAction: () -> Unit
    ) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ ->
            positiveClickAction.invoke()
        }
        builder.show()
    }
}