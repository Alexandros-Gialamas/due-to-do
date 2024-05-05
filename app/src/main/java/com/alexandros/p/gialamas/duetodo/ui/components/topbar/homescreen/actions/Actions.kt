package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.menu.VerticalMenuAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.search.SearchAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.sort.SortAction

@Composable
fun Actions(
    onSearchClicked : () -> Unit,
    onSortClicked : (taskPriority : TaskPriority) -> Unit,
    onMenuItemClicked : () -> Unit,
    onDeleteAllTasksClicked : () -> Unit
){

    MaterialTheme (
        content = {
            SearchAction (onSearchClicked = onSearchClicked )
            SortAction (onSortClicked = onSortClicked)
            VerticalMenuAction(
                onMenuItemClicked = onMenuItemClicked,
                onDeleteAllTasksClicked = onDeleteAllTasksClicked )
        }
    )

}

@Composable
@Preview
private fun ActionsPreview() {
    Row {
        Actions(
            onSearchClicked = {},
            onSortClicked = {},
            onMenuItemClicked = {},
            onDeleteAllTasksClicked = {}
            )
    }
}