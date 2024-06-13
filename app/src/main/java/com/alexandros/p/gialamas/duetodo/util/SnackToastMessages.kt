package com.alexandros.p.gialamas.duetodo.util

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import com.alexandros.p.gialamas.duetodo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class SnackToastMessages(val stringMessage : Int ) {
   object EMPTY_FIELDS : SnackToastMessages( stringMessage = R.string.Validate_Fields )
   object DELETE_ALL_TASKS : SnackToastMessages( stringMessage = R.string.All_Tasks_Deleted_Message )
   object INVALID_TIME : SnackToastMessages( stringMessage = R.string.Invalid_Time )
   object PICK_A_DATE : SnackToastMessages( stringMessage = R.string.Pick_a_Date )
   object RESCHEDULE_REQUEST_ERROR : SnackToastMessages( stringMessage = R.string.Reschedule_Request_Error )


    fun showToast(context: Context) {
        val message = context.getString(stringMessage)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar( // TODO { delete or find the logic }
        context: Context,
        snackBarHostState: SnackbarHostState,
        databaseAction: DatabaseAction,
        scope : CoroutineScope
    ){
        val label = if (databaseAction.name == DatabaseAction.DELETE.toString()) {
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

    fun showGeneralSnackBar( // TODO { delete or find the logic }
        modifier: Modifier = Modifier,
        context: Context,
        snackBarHostState: SnackbarHostState,
        scope : CoroutineScope
    ){
        val label = context.getString(R.string.Snack_Bar_Label_OK)

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