package com.alexandros.p.gialamas.duetodo.util

import android.content.Context
import android.widget.Toast
import com.alexandros.p.gialamas.duetodo.R

sealed class ToastMessages(val stringMessage : Int ) {
   object EMPTY_FIELDS : ToastMessages( stringMessage = R.string.Validate_Fields )

    fun showToast(context: Context) {
        val message = context.getString(stringMessage)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}