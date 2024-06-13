package com.alexandros.p.gialamas.duetodo.ui.components.snackbar


import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    scope : CoroutineScope,
    onComplete: (DatabaseAction) -> Unit,
    onUndoClicked: (DatabaseAction) -> Unit,
    taskTitle: String,
    databaseAction: DatabaseAction,
) {



    LaunchedEffect(key1 = databaseAction) {
        if (databaseAction != DatabaseAction.NO_ACTION) {
            scope.launch {
                val snackBarResult =
                    snackBarHostState.showSnackbar(
                        message = setMessage(databaseAction = databaseAction, taskTitle = taskTitle),
                        actionLabel = setActionLabel(databaseAction),
                        duration = SnackbarDuration.Short
                    )
//                if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
//                    onUndoClicked(Action.UNDO)
//                } else if (snackBarResult == SnackbarResult.Dismissed || action != Action.DELETE) {
//                    onComplete(Action.NO_ACTION)
//                }
                undoDeletedTask(
                    databaseAction = databaseAction,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(DatabaseAction.NO_ACTION)
        }
    }
}


private fun setMessage(databaseAction: DatabaseAction, taskTitle: String): String {
    return when (databaseAction) {
        DatabaseAction.DELETE_ALL -> "All Tasks Deleted" //TODO { skip hardcode }
        else -> "${databaseAction.name} : $taskTitle"  //TODO { better messages }
    }
}

private fun setActionLabel(databaseAction: DatabaseAction): String {
    return if (databaseAction.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    databaseAction: DatabaseAction,
    snackBarResult: SnackbarResult,
    onUndoClicked: (DatabaseAction) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && databaseAction == DatabaseAction.DELETE) {
        onUndoClicked(DatabaseAction.UNDO)
    }
}