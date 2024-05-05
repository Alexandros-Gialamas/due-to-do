package com.alexandros.p.gialamas.duetodo.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(messageType : String){
    Toast.makeText( this, messageType, Toast.LENGTH_SHORT).show()
}