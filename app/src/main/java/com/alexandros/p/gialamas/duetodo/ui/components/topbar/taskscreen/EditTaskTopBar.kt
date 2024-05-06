package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.CloseAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.DeleteAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.EditTaskTopBarActions
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.UpdateAction
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor
import com.alexandros.p.gialamas.duetodo.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskTopBar(
    selectedTask : TaskTable?,
    navigateToHomeScreen : (Action) -> Unit,
) {
    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigateToHomeScreen) },
        title = {
            selectedTask?.title?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.topAppBarrContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.topAppBarrBackgroundColor),
        actions = {
            EditTaskTopBarActions(
                selectedTask = selectedTask,
                navigateToHomeScreen = navigateToHomeScreen
            )
        }
    )
}


@Composable
@Preview
private fun EditTaskTopBarPreview() {
    EditTaskTopBar(
        navigateToHomeScreen = {},
        selectedTask = TaskTable(
            taskId = 0,
            title = "Go to Gym",
            description = "Tomorrow at 5 pm",
            taskPriority = TaskPriority.MEDIUM)
        )
}