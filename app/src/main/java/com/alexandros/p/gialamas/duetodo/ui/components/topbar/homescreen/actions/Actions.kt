package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.DisplayAlertDialog
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.layout.LayoutAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.menu.MenuAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.menu.VerticalMenuAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.sort.SortAction

@Composable
fun Actions(
    onLayoutClicked : () -> Unit,
    onSortClicked : (taskPriority : TaskPriority) -> Unit,
    onMenuItemClicked : () -> Unit,
    onMenuClicked : () -> Unit,
    onDeleteAllTasksClicked : () -> Unit
){

    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(id = R.string.Delete_All_Task),
        message = stringResource(id = R.string.Delete_All_Task_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllTasksClicked() }
    )



            MenuAction(onMenuClicked = onMenuClicked)
            SortAction (onSortClicked = onSortClicked)
            LayoutAction (onLayoutClicked = onLayoutClicked)
            VerticalMenuAction(
                onMenuItemClicked = onMenuItemClicked,
                onDeleteAllTasksClicked = { openDialog = true } )


}

@Composable
@Preview
private fun ActionsPreview() {
    Row {
        Actions(
            onMenuClicked = {},
            onSortClicked = {},
            onLayoutClicked = {},
            onMenuItemClicked = {},
            onDeleteAllTasksClicked = {}
            )
    }
}