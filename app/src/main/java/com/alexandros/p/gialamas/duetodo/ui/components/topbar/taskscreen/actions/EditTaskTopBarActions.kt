package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.DisplayAlertDialog
import com.alexandros.p.gialamas.duetodo.util.Action

@Composable
fun EditTaskTopBarActions(
    selectedTask: TaskTable?,
    navigateToHomeScreen: (Action) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    var openDialog by remember { mutableStateOf(false) }

    if (selectedTask != null) {

        DisplayAlertDialog(
            title = stringResource(id = R.string.Delete_Task, selectedTask.title),
            message = stringResource(id = R.string.Delete_Task_confirmation, selectedTask.title),
            openDialog = openDialog,
            closeDialog = { openDialog = false },
            onYesClicked = { navigateToHomeScreen(Action.DELETE) },
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )

        DeleteAction(
            onDeleteClicked = { openDialog = true },
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )

        UpdateAction(
            onUpdateClicked = navigateToHomeScreen,
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    }
}