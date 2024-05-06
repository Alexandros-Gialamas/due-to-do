package com.alexandros.p.gialamas.duetodo.util

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager

fun Context.showToast(messageType : String){
    Toast.makeText( this, messageType, Toast.LENGTH_SHORT).show()
}

fun Modifier.detectOutsideClicks(
    focusManager: FocusManager,
//    onClickOutside: () -> Unit
): Modifier = this.then(
    Modifier.clickable {
        focusManager.clearFocus()
//        onClickOutside()
    }
)