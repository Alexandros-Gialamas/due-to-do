package com.alexandros.p.gialamas.duetodo.util

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.alexandros.p.gialamas.duetodo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class SnackToastMessages(val stringMessage : Int ) {
   object EMPTY_FIELDS : SnackToastMessages( stringMessage = R.string.Validate_Fields )
   object DELETE_ALL_TASKS : SnackToastMessages( stringMessage = R.string.All_Tasks_Deleted_Message )

    fun showToast(context: Context) {
        val message = context.getString(stringMessage)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar( // TODO { delete or find the logic }
        context: Context,
        snackBarHostState: SnackbarHostState,
        action: Action,
        scope : CoroutineScope
    ){
        val label = if (action.name == Action.DELETE.toString()) {
            context.getString(R.string.Snack_Bar_Label_UNDO)
        } else {
            context.getString(R.string.Snack_Bar_Label_OK)
        }
        val message = context.getString(stringMessage)
       scope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = label,
                duration = SnackbarDuration.Short
            )
        }
    }
}