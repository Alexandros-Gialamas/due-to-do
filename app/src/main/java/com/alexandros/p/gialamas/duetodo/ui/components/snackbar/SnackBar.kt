package com.alexandros.p.gialamas.duetodo.ui.components.snackbar


import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    scope : CoroutineScope,
    onComplete: (CrudAction) -> Unit,
    onUndoClicked: (CrudAction) -> Unit,
    taskTitle: String,
    crudAction: CrudAction,
) {



    LaunchedEffect(key1 = crudAction) {
        if (crudAction != CrudAction.NO_ACTION) {
            scope.launch {
                val snackBarResult =
                    snackBarHostState.showSnackbar(
                        message = setMessage(crudAction = crudAction, taskTitle = taskTitle),
                        actionLabel = setActionLabel(crudAction),
                        duration = SnackbarDuration.Short
                    )
//                if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
//                    onUndoClicked(Action.UNDO)
//                } else if (snackBarResult == SnackbarResult.Dismissed || action != Action.DELETE) {
//                    onComplete(Action.NO_ACTION)
//                }
                undoDeletedTask(
                    crudAction = crudAction,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(CrudAction.NO_ACTION)
        }
    }
}


private fun setMessage(crudAction: CrudAction, taskTitle: String): String {
    return when (crudAction) {
        CrudAction.DELETE_ALL -> "All Tasks Deleted" //TODO { skip hardcode }
        else -> "${crudAction.name} : $taskTitle"  //TODO { better messages }
    }
}

private fun setActionLabel(crudAction: CrudAction): String {
    return if (crudAction.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    crudAction: CrudAction,
    snackBarResult: SnackbarResult,
    onUndoClicked: (CrudAction) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && crudAction == CrudAction.DELETE) {
        onUndoClicked(CrudAction.UNDO)
    }
}