package com.alexandros.p.gialamas.duetodo.ui.components


import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages
import kotlinx.coroutines.launch

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    onComplete : (Action) -> Unit,
//    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action,
    context: Context
) {
//    handleDatabaseActions()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult =
//                    SnackToastMessages.DELETE_ALL_TASKS.showSnackBar(
//                        context = context,
//                        snackBarHostState = snackBarHostState,
//                        action = action,
//                        scope = scope,
//                    )

                    snackBarHostState.showSnackbar(
                        message = setMessage(action = action, taskTitle = taskTitle),
                        actionLabel = setActionLabel(action),
                        duration = SnackbarDuration.Short
                    )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }
}


private fun setMessage(action: Action, taskTitle: String) : String {
    return when (action){
        Action.DELETE_ALL -> "All Tasks Deleted" //TODO { skip hardcode }
        else -> "${action.name} : $taskTitle"  //TODO { better messages }
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}